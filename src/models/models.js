const {db} = require('../config/db.config');

// Get data from the 'nasabah' table
async function getDataNasabah() {
  try {
    const query = 'SELECT * FROM nasabah';
    const results = await db.query(query);
    return results.rows;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

// Get data from the 'transactions' table
async function getTransaksibyId(user_id) {
  try {
    const query = 'SELECT * FROM transactions WHERE sender_id = $1 OR recipient_id = $1';
    const values = [user_id];
    const results = await db.query(query, values);
    return results.rows ;
  } catch (err) {
    console.log(err);
    throw err;
  }
}


// Insert data into the 'nasabah' table
async function tambahNasabah(data) {
  const { user_id, name, address, phonenumber, balance, job } = data;
  const query = `
    INSERT INTO nasabah (user_id, name, address, phonenumber, balance, job)
    VALUES ($1, $2, $3, $4, $5, $6)
  `;
  const values = [user_id, name, address, phonenumber, balance, job];

  try {
    await db.query(query, values);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

// Transfer funds between 'nasabah' and update 'transactions'
async function transferFunds(data) {
  const { sender_id, recipient_id, amount } = data;

  try {
    const client = await db.connect();
    try {
      await client.query('BEGIN');

      // Update sender's balance
      const updateSenderQuery = 'UPDATE nasabah SET balance = balance - $1 WHERE user_id = $2';
      const updateSenderValues = [amount, sender_id];
      await client.query(updateSenderQuery, updateSenderValues);

      // Update recipient's balance
      const updateRecipientQuery = 'UPDATE nasabah SET balance = balance + $1 WHERE user_id = $2';
      const updateRecipientValues = [amount, recipient_id];
      await client.query(updateRecipientQuery, updateRecipientValues);

      // Insert transaction
      const insertTransactionQuery = 'INSERT INTO transactions (sender_id, recipient_id, amount) VALUES ($1, $2, $3)';
      const insertTransactionValues = [sender_id, recipient_id, amount];
      await client.query(insertTransactionQuery, insertTransactionValues);

      await client.query('COMMIT');
    } catch (error) {
      await client.query('ROLLBACK');
      throw error;
    } finally {
      client.release();
    }
  } catch (err) {
    console.log(err);
    throw err;
  }
}

async function withdrawFunds(user_id, amount) {
  try {
    const client = await db.connect();
    try {
      await client.query('BEGIN');

      // Check if the user exists
      const checkUserQuery = 'SELECT * FROM nasabah WHERE user_id = $1';
      const checkUserValues = [user_id];
      const userResult = await client.query(checkUserQuery, checkUserValues);
      const user = userResult.rows[0];

      if (!user) {
        throw new Error('User not found');
      }

      // Check if the user has sufficient balance
      if (user.balance < amount) {
        throw new Error('Insufficient balance');
      }

      // Update user's balance
      const updateBalanceQuery = 'UPDATE nasabah SET balance = balance - $1 WHERE user_id = $2';
      const updateBalanceValues = [amount, user_id];
      await client.query(updateBalanceQuery, updateBalanceValues);

      // Insert transaction record
      const insertTransactionQuery = 'INSERT INTO transactions (sender_id, recipient_id, amount) VALUES ($1, $2, $3)';
      const insertTransactionValues = [user_id, null, amount];
      await client.query(insertTransactionQuery, insertTransactionValues);

      await client.query('COMMIT');
    } catch (error) {
      await client.query('ROLLBACK');
      throw error;
    } finally {
      client.release();
    }
  } catch (err) {
    console.log(err);
    throw err;
  }
}

module.exports = {
  getDataNasabah,
  getTransaksibyId,
  tambahNasabah,
  transferFunds,
  withdrawFunds,
};

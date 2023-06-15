const { db } = require('../config/db.config');


// Login as admin
async function loginAdmin(username, password, req, res) {
  const query = 'SELECT * FROM admin WHERE username = $1 AND password = $2';
  const values = [username, password];
  try {
    const result = await db.query(query, values);
    const user = result.rows[0];
    if(!user) {
      throw { statusCode: 404, message: 'Invalid username or password' };
    }
    // Create session and store user data
    req.session.user = user;
    console.log(req.session.user);
    res.send('Admin logged in successfully');
  } catch (err) {
    console.error(err);
    res.status(404).send('Invalid username or password');
  }
}


// Login a user
async function loginUser(username, password, req, res) {
  const query = 'SELECT * FROM nasabah WHERE username = $1 AND password = $2';
  const values = [username, password];
  try {
    const result = await db.query(query, values);
    const user = result.rows[0];
    if(!user) {
      throw { statusCode: 404, message: 'Invalid username or password' };
    }
    // Create session and store user data
    req.session.user = user;
    console.log(req.session.user);
    res.send('success');
  } catch (err) {
    console.error(err);
    res.status(404).send('Invalid username or password');
  }
}

// Logout 

// Insert data into the 'nasabah' table
async function tambahNasabah(data) {
  const { user_id, name, address, phonenumber, balance, job, email, password } = data;
  const query = `
    INSERT INTO nasabah (user_id, name, address, phonenumber, balance, job, email, password)
    VALUES ($1, $2, $3, $4, $5, $6, $7, $8)
  `;
  const values = [user_id, name, address, phonenumber, balance, job, email, password];

  try {
    await db.query(query, values);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

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
async function getAllTransaksi() {
  try {
    const query = 'SELECT * FROM transactions';
    const results = await db.query(query);
    return results.rows;
  } catch (err) {
    console.log(err);
    throw err;
  }
}


// Get data from the 'transactions' table
async function getTransaksibyId(user_id){
  try {
    const query = 'SELECT * FROM transactions WHERE sender_id = $1 OR recipient_id = $1';
    const values = [user_id];
    const results = await db.query(query, values);
    return results.rows;
  } catch (err) {
    console.log(err);
    throw err;
  }
}





// Transfer funds between 'nasabah' and update 'transactions'
async function transferFunds(data) {
  const { sender_id, recipient_id, amount, transaction_type } = data;

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
      const insertTransactionQuery = 'INSERT INTO transactions (sender_id, recipient_id, amount, transaction_type) VALUES ($1, $2, $3, $4)';
      const insertTransactionValues = [sender_id, recipient_id, amount, transaction_type];
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

      const checkUserQuery = 'SELECT * FROM nasabah WHERE user_id = $1';
      const checkUserValues = [user_id];
      const userResult = await client.query(checkUserQuery, checkUserValues);
      const user = userResult.rows[0];

      if (!user) {
        throw new Error('User not found');
      }
      user.balance = parseInt(user.balance);
      if (user.balance < amount) {
        throw new Error('Insufficient balance');
      }

      const updateBalanceQuery = 'UPDATE nasabah SET balance = balance - $1 WHERE user_id = $2';
      const updateBalanceValues = [amount, user_id];
      await client.query(updateBalanceQuery, updateBalanceValues);

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
    console.error(err);
    throw err;
  }
}

// Deposit funds to 'nasabah' and update 'transactions'
async function depositFunds(user_id, amount, transaction_type) {
  try {
    const client = await db.connect();
    try {
      await client.query('BEGIN');

      const checkUserQuery = 'SELECT * FROM nasabah WHERE user_id = $1';
      const checkUserValues = [user_id];
      const userResult = await client.query(checkUserQuery, checkUserValues);
      const user = userResult.rows[0];

      if (!user) {
        throw new Error('User not found');
      }

      const updateBalanceQuery = 'UPDATE nasabah SET balance = balance + $1 WHERE user_id = $2';
      const updateBalanceValues = [amount, user_id];
      await client.query(updateBalanceQuery, updateBalanceValues);

      const insertTransactionQuery = 'INSERT INTO transactions (sender_id, recipient_id, amount, transaction_type) VALUES ($1, $2, $3, $4)';
      const insertTransactionValues = [null, user_id, amount, transaction_type];
      await client.query(insertTransactionQuery, insertTransactionValues);

      await client.query('COMMIT');
    } catch (error) {
      await client.query('ROLLBACK');
      throw error;
    } finally {
      client.release();
    }
  } catch (err) {
    console.error(err);
    throw err;
  }
}



module.exports = {
  loginAdmin,
  loginUser,
  getAllTransaksi,
  getDataNasabah,
  getTransaksibyId,
  tambahNasabah,
  transferFunds,
  withdrawFunds,
  depositFunds,
};

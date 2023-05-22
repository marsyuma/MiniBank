const db = require('../config/db.config');

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
async function getTransaksi() {
  try {
    const query = 'SELECT * FROM transactions';
    const results = await db.query(query);
    return results.rows;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

// Insert data into the 'nasabah' table
async function insertData(data) {
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
  const query = `
    BEGIN;
    UPDATE nasabah SET balance = balance - $1 WHERE user_id = $2;
    UPDATE nasabah SET balance = balance + $1 WHERE user_id = $3;
    INSERT INTO transactions (sender_id, recipient_id, amount) VALUES ($2, $3, $1);
    COMMIT;
  `;
  const values = [amount, sender_id, recipient_id];

  try {
    await db.query(query, values);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

module.exports = {
  getDataNasabah,
  getTransaksi,
  insertData,
  transferFunds,
};

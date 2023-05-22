const db = require('../models/models');

// Get data from the 'nasabah' table
async function getDataNasabah(req, res) {
  try {
    const data = await models.getDataNasabah();
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Get data from the 'transactions' table
async function getTransaksi(req, res) {
  try {
    const data = await models.getTransaksi();
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Insert data into the 'nasabah' table
async function insertData(req, res) {
  try {
    const { user_id, name, address, phonenumber, balance, job } = req.body;
    await models.insertData({ user_id, name, address, phonenumber, balance, job });
    res.send('Insert Berhasil');
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Transfer funds between 'nasabah' and update 'transactions'
async function transferFunds(req, res) {
  try {
    const { sender_id, recipient_id, amount } = req.body;
    await models.transferFunds({ sender_id, recipient_id, amount });
    res.send(`Successfully transferred ${amount} balance from user_id ${sender_id} to user_id ${recipient_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

module.exports = {
  getDataNasabah,
  getTransaksi,
  insertData,
  transferFunds,
};

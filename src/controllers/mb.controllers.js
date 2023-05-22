const db = require('../models/models');

// Get data from the 'nasabah' table
async function getDataNasabah(req, res) {
  try {
    const data = await db.getDataNasabah();
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Get data from the 'transactions' table
async function getTransaksibyId(req, res) {
  try {
    const data = await db.getTransaksibyId(req.body.user_id); // Pass the user_id from request parameters
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}


// Insert data into the 'nasabah' table
async function tambahNasabah(req, res) {
  try {
    const { user_id, name, address, phonenumber, balance, job } = req.body;
    await db.tambahNasabah({ user_id, name, address, phonenumber, balance, job });
    res.send('Data Nasabah Berhasil Ditambahkan');
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Transfer funds between 'nasabah' and update 'transactions'
async function transferFunds(req, res) {
  try {
    const { sender_id, recipient_id, amount } = req.body;
    await db.transferFunds({ sender_id, recipient_id, amount });
    res.send(`Successfully transferred ${amount} balance from user_id ${sender_id} to user_id ${recipient_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

async function withdrawFunds(req, res) {
  try {
    const { user_id, amount } = req.body;

    // Perform withdrawal operation
    await db.withdrawFunds(user_id, amount);

    res.send(`Successfully withdrew ${amount} from user_id ${user_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

module.exports = {
  getDataNasabah,
  getTransaksibyId,
  tambahNasabah,
  transferFunds,
  withdrawFunds
};

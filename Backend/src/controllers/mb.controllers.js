const db = require('../models/models');



// Login as admin
async function loginAdmin(req, res) {
  const { username, password } = req.body;
  try {
    await db.loginAdmin(username, password, req, res);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}


// Login a user
async function loginUser(req, res) {
  const { username, password } = req.body;
  try {
    await db.loginUser(username, password, req, res);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Logout 
async function logout(req, res) {
  try {
    req.session.destroy();
    res.send('Logged out successfully');
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Get data from the 'nasabah' table
async function getDataNasabahById(req, res) {
  const user = req.session.user.user_id;
  try {
    const data = await db.getDataNasabahById(user);
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}


// Insert data into the 'nasabah' table
async function tambahNasabah(req, res) {
  try {
    if(req.session.user.role_id != 1){
      throw { statusCode: 403, message: 'Forbidden' };
    }
    const { user_id, name, address, phonenumber, balance, username, job, email, password } = req.body;
    await db.tambahNasabah({ user_id, name, address, phonenumber, balance, username, job, email, password });
    res.send('Data Nasabah Berhasil Ditambahkan');
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Get data from the 'nasabah' table
async function getDataNasabah(req, res) {
  const role_id = req.session.user.role_id;
  try {
    if (role_id !== 1) {
      throw { statusCode: 403, message: 'Forbidden' };
    }
    const data = await db.getDataNasabah();
    res.send(data);
  } catch (err) {
    console.log(err);
    if (err.statusCode === 403) {
      res.status(err.statusCode).send(err.message);
    } else {
      res.status(500).send('Internal Server Error');
    }
  }
}


// Get data from the 'transactions' table
async function getAllTransaksi(req, res) {
  const role_id = req.session.user.role_id;
  try {
    if(role_id != 1){
      throw { statusCode: 403, message: 'Forbidden' };
    }
    const data = await db.getAllTransaksi();
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Get data from the 'transactions' table
async function getTransaksibyId(req, res) {
  const user = req.session.user.user_id;
  try {
    const data = await db.getTransaksibyId(user);
    res.send(data);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}




// Transfer funds between 'nasabah' and update 'transactions'
async function transferFunds(req, res) {
  const transaction_type = 1; // 1 for transfer
  const sender_id = req.session.user.user_id;
  try {
    const { recipient_id, amount } = req.body;
    if (amount > req.session.user.balance) {
      throw { statusCode: 400, message: 'Insufficient balance' };
    }
    await db.transferFunds({ sender_id, recipient_id, amount, transaction_type });
    res.send(`Successfully transferred ${amount} balance from user_id ${sender_id} to user_id ${recipient_id}`);
    console.log(`Successfully transferred ${amount} balance from user_id ${sender_id} to user_id ${recipient_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Withdraw funds from 'nasabah' and update 'transactions'
async function withdrawFunds(req, res) {
  const transaction_type = 3; // 3 for withdraw
  const sender_id = req.session.user.user_id;
  const recipient_id = null;
  try {
    const { amount } = req.body;
    await db.withdrawFunds({ sender_id, recipient_id, amount, transaction_type });
    res.send(`Successfully withdrew ${amount} from user_id ${sender_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

// Deposit funds into 'nasabah' and update 'transactions'
async function depositFunds(req, res) {
  const transaction_type = 2; // 2 for deposit
  const role_id = req.session.user.role_id;

  if (role_id !== 1) {
    throw { statusCode: 403, message: 'Forbidden' };
  }

  try {
    const { user_id, amount } = req.body;
    await db.depositFunds(user_id, amount, transaction_type);
    res.send(`Successfully deposited ${amount} into user_id ${user_id}`);
  } catch (err) {
    console.log(err);
    res.status(500).send('Internal Server Error');
  }
}

module.exports = {
  loginAdmin,
  loginUser,
  logout,
  getAllTransaksi,
  getDataNasabah,
  getTransaksibyId,
  tambahNasabah,
  transferFunds,
  withdrawFunds,
  depositFunds,
  getDataNasabahById
};

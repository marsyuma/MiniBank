const express = require('express');
const router = express.Router();
const db = require('../models/models');
const mbController = require('../controllers/mb.controllers');

// Define your routes here
router.get('/info/data_nasabah', mbController.getDataNasabah);
router.get('/info/transaksi_id', mbController.getTransaksibyId);
//router.get('/info/transaksi', mbController.getAllTransaksi)

router.post('/register/tambah_nasabah', mbController.tambahNasabah);

router.post('/transaction/transfer', mbController.transferFunds);
router.post('/transaction/withdraw', mbController.withdrawFunds);
module.exports = router;

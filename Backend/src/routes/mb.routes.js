const express = require('express');
const router = express.Router();
const db = require('../models/models');
const mbController = require('../controllers/mb.controllers');

// Make / route if hit will return 'Hello World'


// Admin Route
router.get('/admin/data_nasabah', mbController.getDataNasabah);
router.post('/admin/login', mbController.loginAdmin);
router.get('/admin/transaksi', mbController.getAllTransaksi);
router.post('/admin/tambah_nasabah', mbController.tambahNasabah)
router.post('/admin/transaction/deposit', mbController.depositFunds);

// User Route
router.post('/user/login', mbController.loginUser);
router.get('/user/history', mbController.getTransaksibyId);

// Logout
router.post('/logout', mbController.logout);

router.get('/user/profile', mbController.getDataNasabahById);
router.post('/transaction/transfer', mbController.transferFunds);
router.post('/transaction/withdraw', mbController.withdrawFunds);

module.exports = router;

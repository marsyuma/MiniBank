const express = require('express');
const router = express.Router();
const db = require('../models/models');
const mbController = require('../controllers/mb.controllers');

// Define your routes here
router.get('/data_nasabah', mbController.getDataNasabah);
router.get('/transaksi', mbController.getTransaksi);
router.post('/insert', mbController.insertData);
router.post('/transfer', mbController.transferFunds);

module.exports = router;

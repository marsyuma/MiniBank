const express = require('express');
const app = express();
const routes = require('./src/routes/mb.routes');
const mbController = require('./src/controllers/mb.controllers');

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use('/', routes);
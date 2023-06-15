const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const session = require('express-session');
const routes = require('./src/routes/mb.routes');
const mbController = require('./src/controllers/mb.controllers');
const { testDatabaseConnection } = require('./src/config/db.config');
const app = express();

//Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// Session
app.use(session({
  secret: '1234',
  resave: false,
  saveUninitialized: false
}));



app.use('/', routes);

//
testDatabaseConnection();
// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
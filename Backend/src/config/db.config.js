const { Pool } = require('pg');
const dotenv = require('dotenv');

dotenv.config();

console.log(process.env.DB_HOST);
console.log(process.env.DB_PORT);
console.log(process.env.DB_USER);
console.log(process.env.DB_PASSWORD);
console.log(process.env.DB_NAME);

const db = new Pool({
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,  
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  ssl: {
    rejectUnauthorized: true,
  },
});

// Test the database connection
async function testDatabaseConnection() {
  try {
    const client = await db.connect();
    console.log('Connected to the database');
    client.release();
  } catch (error) {
    console.error('Error connecting to the database:', error);
  }
}

module.exports = {
  testDatabaseConnection,
  db,
};

const { Pool } = require('pg');
const dotenv = require('dotenv');

dotenv.config();

const db = new Pool({
  host: 'ep-calm-pond-394042.ap-southeast-1.aws.neon.tech',
  database: 'minibank',
  user: 'bintang.marsyuma.bm',
  password: 'g09KoXHkQBbO',
  port: '5432',
  ssl: {
    rejectUnauthorized: false,
    sslmode: 'require',
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

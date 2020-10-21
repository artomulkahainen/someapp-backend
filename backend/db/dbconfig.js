const { Pool } = require("pg");
const config = require("../utils/config");

const pool = new Pool({
  user: config.PG_USER,
  host: config.PG_URI,
  port: config.PG_PORT,
  database: config.PG_DATABASE,
  password: config.PG_PASSWORD,
});

module.exports = {
  query: (text, array, params) => pool.query(text, array, params),
};

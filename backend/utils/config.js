require("dotenv").config();

const PORT = process.env.PORT;
const PG_URI = process.env.PG_URI;
const PG_USER = process.env.PG_USER;
const PG_PASSWORD = process.env.PG_PASSWORD;
const PG_DATABASE = process.env.PG_DATABASE;
const PG_PORT = process.env.PG_PORT;

module.exports = { PORT, PG_URI, PG_PASSWORD, PG_DATABASE, PG_USER, PG_PORT };

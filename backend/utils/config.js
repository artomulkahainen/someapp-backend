require("dotenv").config();
const { Sequelize } = require("sequelize");

const sequelize = new Sequelize(process.env.DEV_PG_URL);

const PORT = process.env.PORT;

module.exports = {
  PORT,
  sequelize,
};

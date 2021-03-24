require("dotenv").config();

module.exports = {
  development: {
    url: process.env.DEV_PG_URL,
    dialect: "postgres",
  },
  test: {
    url: process.env.TEST_PG_URL,
    dialect: "postgres",
  },
  production: {
    url: process.env.DATABASE_URL,
    dialect: "postgres",
  },
};

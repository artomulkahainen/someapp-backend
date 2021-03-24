const loginRouter = require("express").Router();
const config = require("../utils/config");
const userModel = require("../database/models/user");
const { Sequelize } = require("sequelize");
const User = userModel(config.sequelize, Sequelize);
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

// LOGIN
loginRouter.post("/", async (req, res) => {
  const user = await User.findOne({ where: { username: req.body.username } });

  const correctPassword = !user
    ? false
    : await bcrypt.compare(req.body.password, user.passwordHash);

  if (!(user && correctPassword)) {
    return res.status(401).json({ error: "JsonWebTokenError" });
  }

  const userForToken = {
    username: user.username,
    id: user.id,
  };

  const token = jwt.sign(userForToken, process.env.SECRET);

  res.status(200).json({ token, username: user.username, id: user.id });
});

module.exports = loginRouter;

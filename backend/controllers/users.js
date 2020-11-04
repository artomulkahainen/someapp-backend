const usersRouter = require("express").Router();
const config = require("../utils/config");
const userModel = require("../database/models/user");
const { Sequelize } = require("sequelize");
const User = userModel(config.sequelize, Sequelize);

// GET METHODS
usersRouter.get("/", async (req, res) => {
  try {
    const users = await User.findAll();
    return res.status(200).json({ users });
  } catch (error) {
    return res.status(400).json({ error: error });
  }
});

// POST METHODS
usersRouter.post("/", async (req, res) => {
  if (!req.body.username || !req.body.password) {
    return res
      .status(400)
      .json({ error: "username or password was not provided" });
  }

  const newUser = User.build({
    username: req.body.username,
    passwordHash: req.body.password,
  });

  try {
    await newUser.save();
    return res.status(200).json({ newUser });
  } catch (error) {
    return res.status(400).json({ error: error });
  }
});

module.exports = usersRouter;

const usersRouter = require("express").Router();
const queries = require("../db/queries");

// GET METHODS
usersRouter.get("/", (req, res) => {
  queries.getAllUsers(req, res);
});

// GET USER BY ID
usersRouter.get("/:id", (req, res) => {
  queries.getUserById(req, res);
});

// POST METHODS
usersRouter.post("/", (req, res) => {
  queries.createUser(req, res);
});

module.exports = usersRouter;

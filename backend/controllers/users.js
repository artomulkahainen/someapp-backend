const usersRouter = require("express").Router();
const db = require("../db/dbconfig");

// GET METHODS
usersRouter.get("/", (req, res) => {
  db.query("SELECT * FROM users", (err, result) => {
    !err ? res.json(result.rows) : res.status(400).json({ error: err });
  });
});

// GET USER BY ID
usersRouter.get("/:id", (req, res) => {
  const query = {
    text: "SELECT * FROM users WHERE id = $1",
    values: [req.params.id],
  };

  db.query(query, (err, result) => {
    !err
      ? result.rows.length > 0
        ? res.json(result.rows)
        : res.status(404).json({ error: "user not found" })
      : res.status(400).json({ error: err });
  });
});

// POST METHODS
usersRouter.post("/", (req, res) => {
  const query = {
    text: "INSERT INTO users(username, password) VALUES($1, $2)",
    values: [req.body.username, req.body.password],
  };
  db.query(query, (err, result) => {
    !err
      ? res.status(200).json(req.body)
      : res.status(400).json({ error: err });
  });
});

module.exports = usersRouter;

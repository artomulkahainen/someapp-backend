const db = require("./dbconfig");
const url = require("url");

// ****************** USER QUERIES ******************//
// **************************************************//

const getAllUsers = (req, res) => {
  db.query("SELECT id, username FROM users", (err, result) => {
    !err ? res.json(result.rows) : res.status(400).json({ error: err });
  });
};

const getUserById = (req, res) => {
  db.query(
    "SELECT id, username FROM users WHERE id = $1",
    [req.params.id],
    (err, result) => {
      !err
        ? result.rows.length > 0
          ? res.json(result.rows)
          : res.status(404).json({ error: "user not found" })
        : res.status(400).json({ error: err });
    }
  );
};

const createUser = (req, res) => {
  db.query(
    "INSERT INTO users(username, password) VALUES($1, $2)",
    [req.body.username, req.body.password],
    (err, result) => {
      !err
        ? res.status(200).json(req.body)
        : res.status(400).json({ error: err });
    }
  );
};

// ****************** POST QUERIES ******************//
// **************************************************//

const getAllPosts = (req, res) => {
  const queryObject = url.parse(req.url, true).query;

  // IF USER ID IS NOT GIVEN, GET ALL POSTS
  if (!queryObject.userId) {
    db.query("SELECT * FROM posts", (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    });

    // IF USER ID IS GIVEN AS QUERY PARAM, GET SPECIFIC USER'S POSTS
  } else {
    db.query(
      "SELECT * FROM posts WHERE user_id = $1",
      [queryObject.userId],
      (err, result) => {
        !err ? res.json(result.rows) : res.status(400).json({ error: err });
      }
    );
  }
};

const getPostById = (req, res) => {
  db.query(
    "SELECT * FROM posts WHERE id = $1",
    [req.params.id],
    (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    }
  );
};

const newPost = (req, res) => {
  db.query(
    "INSERT INTO posts(post, user_id) VALUES ($1, $2)",
    [req.body.post, req.body.userId],
    (err, result) => {
      !err ? res.json(req.body) : res.status(400).json({ error: err });
    }
  );
};

// ****************** LIKES QUERIES ******************//
// ***************************************************//

// ****************** COMMENTS QUERIES ******************//
// ******************************************************//

module.exports = {
  getAllUsers,
  getUserById,
  createUser,
  getAllPosts,
  getPostById,
  newPost,
};

const postsRouter = require("express").Router();
const db = require("../db/dbconfig");
const url = require("url");

// GET ALL POSTS
postsRouter.get("/", (req, res) => {
  const queryObject = url.parse(req.url, true).query;

  // IF USER ID IS NOT GIVEN, GET ALL POSTS
  if (!queryObject.userId) {
    db.query("SELECT * FROM posts", (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    });

    // IF USER ID IS GIVEN AS URL PARAM, GET SPECIFIC USER'S POSTS
  } else {
    const query = {
      text: "SELECT * FROM posts WHERE user_id = $1",
      values: [queryObject.userId],
    };
    db.query(query, (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    });
  }
});

// GET POST BY POST ID
postsRouter.get("/:id", (req, res) => {
  const query = {
    text: "SELECT * FROM posts WHERE id = $1",
    values: [req.params.id],
  };
  db.query(query, (err, result) => {
    !err ? res.json(result.rows) : res.status(400).json({ error: err });
  });
});

// GET POST COMMENTS BY POST ID

// POST A NEW POST
postsRouter.post("/", (req, res) => {
  const query = {
    text: "INSERT INTO posts(post, user_id) VALUES ($1, $2)",
    values: [req.body.post, req.body.userId],
  };
  db.query(query, (err, result) => {
    !err ? res.json(req.body) : res.status(400).json({ error: err });
  });
});

// LIKE A POST
postsRouter.post("/like", (req, res) => {
  const queryObject = url.parse(req.url, true).query;

  if (!queryObject.postId || !queryObject.userId) {
    return res
      .status(400)
      .json({ error: "post id and/or user id was not given" });
  } else {
    const query = {
      text: "INSERT INTO postlikes(user_id, post_id) VALUES ($1, $2)",
      values: [queryObject.userId, queryObject.postId],
    };
    db.query(query, (err, result) => {
      !err
        ? res.json({
            status: 200,
            postId: queryObject.postId,
            userId: queryObject.userId,
          })
        : res.status(400).json({ error: err });
    });
  }
});

module.exports = postsRouter;

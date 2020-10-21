const likesRouter = require("express").Router();
const db = require("../db/dbconfig");
const url = require("url");

// GET ALL POST LIKES
likesRouter.get("/", async (req, res) => {
  const queryObject = url.parse(req.url, true).query;

  if (!queryObject.postId) {
    res.status(400).json({ error: "postId was not provided" });
  } else {
    const query = {
      text: "SELECT * FROM postlikes WHERE post_id = $1",
      values: [queryObject.postId],
    };
    await db.query(query, (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    });
  }
});

// LIKE A POST
likesRouter.post("/", (req, res) => {
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

module.exports = likesRouter;

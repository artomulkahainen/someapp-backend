const commentsRouter = require("express").Router();
const db = require("../db/dbconfig");
const url = require("url");

// GET ALL COMMENTS OF A POST
commentsRouter.get("/", (req, res) => {
  const queryObject = url.parse(req.url, true).query;
  console.log(queryObject.postId);

  if (!queryObject.postId) {
    res.status(400).json({ error: "postId was not provided" });
  } else {
    const query = {
      text: "SELECT * FROM postcomments WHERE post_id = $1",
      values: [queryObject.postId],
    };
    db.query(query, (err, result) => {
      !err ? res.json(result.rows) : res.status(400).json({ error: err });
    });
  }
});

// POST A COMMENT
commentsRouter.post("/", (req, res) => {
  const queryObject = url.parse(req.url, true).query;
  console.log(queryObject);
  if (!queryObject.postId || !queryObject.userId) {
    return res
      .status(400)
      .json({ error: "PostId and/or userId wasn't provided." });
  } else {
    const query = {
      text:
        "INSERT INTO postcomments(comment_text, post_id, user_id) VALUES ($1,$2,$3)",
      values: [req.body.commentText, queryObject.postId, queryObject.userId],
    };
    db.query(query, (err, result) => {
      !err ? res.json(req.body) : res.status(400).json({ error: err });
    });
  }
});

module.exports = commentsRouter;

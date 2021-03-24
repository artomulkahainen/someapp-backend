const postsRouter = require("express").Router();
const config = require("../utils/config");
const postModel = require("../database/models/post");
const { Sequelize } = require("sequelize");
const Post = postModel(config.sequelize, Sequelize);
const tokenValidator = require("../utils/tokenValidator");

// GET ALL POSTS
postsRouter.get("/", async (req, res) => {
  try {
    const posts = await Post.findAll();
    return res.status(200).json(posts);
  } catch (error) {
    return res.status(400).json({ error: error });
  }
});

// NEW POST
postsRouter.post("/", async (req, res) => {
  const id = await tokenValidator(req);

  if (!id) {
    return res.status(401).json({ error: "jsonwebtoken error" });
  }

  const newPost = Post.build({
    post: req.body.post,
    userId: id,
  });

  try {
    newPost.save();
    return res.status(200).json({ newPost });
  } catch (error) {
    return res.status(400).json({ error: error });
  }
});

module.exports = postsRouter;

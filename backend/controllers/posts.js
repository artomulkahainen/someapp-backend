const postsRouter = require("express").Router();
const config = require("../utils/config");
const postModel = require("../database/models/post");
const { Sequelize } = require("sequelize");
const Post = postModel(config.sequelize, Sequelize);

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
/*postsRouter.post("/", (req, res) => {
  queries.newPost(req, res);
});*/

module.exports = postsRouter;

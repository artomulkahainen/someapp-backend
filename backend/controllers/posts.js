const postsRouter = require("express").Router();
const db = require("../db/dbconfig");
const queries = require("../db/queries");

// GET ALL POSTS
postsRouter.get("/", (req, res) => {
  queries.getAllPosts(req, res);
});

// GET POST BY ID
postsRouter.get("/:id", (req, res) => {
  queries.getPostById(req, res);
});

// NEW POST
postsRouter.post("/", (req, res) => {
  queries.newPost(req, res);
});

module.exports = postsRouter;

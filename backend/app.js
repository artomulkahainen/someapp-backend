const express = require("express");
const app = express();
const cors = require("cors");
const morgan = require("morgan");
const usersRouter = require("./controllers/users");
const postsRouter = require("./controllers/posts");
const commentsRouter = require("./controllers/comments");
const likesRouter = require("./controllers/likes");

morgan.token("body", (req) => {
  return JSON.stringify(req.body);
});

app.use(cors());
app.use(express.json());
app.use(
  morgan(
    ":method :url :status :response-time ms - :res[content-length] :body - :req[content-length]"
  )
);

// ROUTES
app.use("/api/users", usersRouter);
app.use("/api/posts", postsRouter);
app.use("/api/comments", commentsRouter);
app.use("/api/likes", likesRouter);

module.exports = app;

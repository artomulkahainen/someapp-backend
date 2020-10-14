const express = require('express');
const app = express();
const cors = require('cors');
const morgan = require('morgan');
const usersRouter = require('./controllers/users');

morgan.token('body', (req) => {
    return JSON.stringify(req.body);
});

//app.use(cors());
app.use(express.json());
app.use(
    morgan(
        ':method :url :status :response-time ms - :res[content-length] :body - :req[content-length]'
    )
);

app.use('/api/users', usersRouter);

module.exports = app;
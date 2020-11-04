const http = require("http");
const app = require("./app");
const config = require("./utils/config");
const server = http.createServer(app);

config.sequelize
  .sync()
  .then(() => {
    console.log("Successfully connected to Postgres DB");
    server.listen(config.PORT, () => {
      console.log("Server is running on port " + config.PORT);
    });
  })
  .catch((error) => console.log(error));

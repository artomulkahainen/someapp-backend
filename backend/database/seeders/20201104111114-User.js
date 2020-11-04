module.exports = {
  up: (queryInterface, Sequelize) =>
    queryInterface.bulkInsert(
      "Users",
      [
        {
          username: "jaoe",
          passwordHash: "jaoe123",
          admin: false,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
        {
          username: "admin",
          passwordHash: "admin123",
          admin: true,
          createdAt: new Date(),
          updatedAt: new Date(),
        },
      ],
      {}
    ),

  down: (queryInterface, Sequelize) =>
    queryInterface.bulkDelete("Users", null, {}),
};

// database/seeds/xxxx-User.js

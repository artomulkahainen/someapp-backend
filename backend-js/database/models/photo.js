module.exports = (sequelize, DataTypes) => {
  const Photo = sequelize.define(
    "Photo",
    {
      imageUrl: DataTypes.STRING,
      userId: DataTypes.INTEGER,
    },
    {}
  );
  Post.associate = function (models) {
    // associations can be defined here
    Photo.belongsTo(models.User, {
      foreignKey: "userId",
      as: "photos",
      onDelete: "CASCADE",
    });
  };
  return Photo;
};

// database/models/post.js

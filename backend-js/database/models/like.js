module.exports = (sequelize, DataTypes) => {
  const Photo = sequelize.define(
    "Like",
    {
      postId: { type: DataTypes.INTEGER, primaryKey: true },
      userId: { type: DataTypes.INTEGER, primaryKey: true },
    },
    {}
  );
  Post.associate = function (models) {
    // associations can be defined here
    Like.belongsTo(models.User, {
      foreignKey: "userId",
      onDelete: "CASCADE",
    });
    Like.belongsTo(models.Post, {
      foreignKey: "postId",
      onDelete: "CASCADE",
    });
  };
  return Photo;
};

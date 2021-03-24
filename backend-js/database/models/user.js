module.exports = (sequelize, DataTypes) => {
  const User = sequelize.define(
    "User",
    {
      username: { type: DataTypes.STRING, allowNull: false, unique: true },
      passwordHash: { type: DataTypes.STRING, allowNull: false },
      admin: { type: DataTypes.BOOLEAN },
    },
    {}
  );
  User.associate = function (models) {
    // associations can be defined here
    User.hasMany(models.Post, {
      foreignKey: "userId",
      as: "posts",
      onDelete: "CASCADE",
    });

    User.hasMany(models.Comment, {
      foreignKey: "userId",
      as: "comments",
      onDelete: "CASCADE",
    });

    User.hasOne(models.Photo, {
      foreignKey: "userId",
      as: "photos",
      onDelete: "CASCADE",
    });

    User.hasMany(models.Like, {
      foreignKey: "userId",
      as: "likes",
      onDelete: "CASCADE",
    });
  };
  return User;
};

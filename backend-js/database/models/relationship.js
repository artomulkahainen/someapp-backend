"use strict";
const { Model } = require("sequelize");
module.exports = (sequelize, DataTypes) => {
  class Relationship extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  Relationship.init(
    {
      userId: { type: DataTypes.INTEGER, primaryKey: true },
      userId2: { type: DataTypes.INTEGER, primaryKey: true },
      status: DataTypes.INTEGER,
      actionUserId: DataTypes.INTEGER,
    },
    {
      sequelize,
      modelName: "Relationship",
    }
  );
  return Relationship;
};

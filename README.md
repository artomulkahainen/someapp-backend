<h1 align="center">GimmeVibe</h1>

## Table of Contents

* [About the Project](#about-the-project)
    * [Built With](#built-with)
* [How to run dev environment](#how-to-run-dev-environment)
* [How to run IT tests](#how-to-run-it-tests)


## About The Project

I'm building social media app for mobile devices. The purpose of this project is learning to code. 
"GimmeVibe" is the project name for the whole app. This repository contains the backend part of the app.

### Built With

* Spring Boot 2.7.5 (Java)
* PostgreSQL 14
* Docker

## How to run dev environment

To run development mode, you can use Docker to create db.
Otherwise you can install postgres v14 on local machine and use db-name gimmevibe and set username and password to postgres.

Docker command:
docker run -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=gimmevibe -p 5432:5432 --name gimmevibe-dev postgres:14-alpine

Before running application, you have to rename main/resources/application-EXAMPLE.yml to application-development.yml.
You have to also edit the file by inserting spring security username and password and also app.secret.

## How to run IT tests

To run IT tests, you have to use Docker to create testdb.
Create it by running command: 

docker run -d --name gimmevibe-test -p 5433:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=gimmevibe postgres:14-alpine

After that container can be started by running: docker start gimmevibe-test
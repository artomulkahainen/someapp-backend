To run development mode, you can use Docker to create db.
Otherwise you can install postgres v14 on local machine and use db-name gimmevibe and set username and password to postgres.

Docker command:
docker run -d --name gimmevibe-dev -p 5432:5432 -v ~/apps/postgres:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=gimmevibe postgres:14-alpine

Before running application, you have to rename main/resources/application-EXAMPLE.yml to application-development.yml.
You have to also edit the file by inserting spring security username and password and also app.secret.

To run IT tests, you have to use Docker to create testdb.
Create it by running command: 

docker run -d --name gimmevibe-test -p 5433:5432 -v ~/apps/postgres:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=gimmevibe postgres:14-alpine

After that container can be started by running: docker start gimmevibe-test
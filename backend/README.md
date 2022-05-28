To run IT tests, you have to use Docker to create testdb.
Create it by running command: 

docker run -d --name gimmevibe-test -p 5433:5432 -v ~/apps/postgres:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=gimmevibe-test postgres:14-alpine

After that container can be started by running: docker start gimmevibe-test
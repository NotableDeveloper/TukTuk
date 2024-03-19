FROM mysql:latest

COPY create_database.sql /docker-entrypoint-initdb.d
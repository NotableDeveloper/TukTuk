#! /bin/bash
cd docker
docker compose down
docker rmi docker-tuktuk
docker rmi docker-mysql
docker volume rm docker_mysql_volume
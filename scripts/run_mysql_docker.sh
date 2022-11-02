#!/bin/bash

COMMAND=$1 # type is up or down

SCRIPT_REAL=$(realpath -s "$0")
SCRIPT_DIR=$(dirname "$SCRIPT_REAL")

HOMEPARTY_DOCKER_NAME=homeparty_main_db
MAIN_DB_SCHEMA=homeparty_main
TEST_DB_SCHEMA=homeparty_main_test
TEST_PORT=38291

if [ -z "$COMMAND" ]; then
  echo "required command variable (up or down)"
  exit 1
fi

function export-mysql-env() {
  MYSQL_ENV_FILE="$SCRIPT_DIR/../.env.mysql_test"
  touch "$MYSQL_ENV_FILE"
  echo "# Main Mysql
DATASOURCE_MAIN_JDBC_URL=jdbc:mysql://127.0.0.1:$TEST_PORT/$MAIN_DB_SCHEMA
DATASOURCE_MAIN_USERNAME=root
DATASOURCE_MAIN_PASSWORD=mysql

# Test Mysql
DATASOURCE_MAIN_JDBC_URL=jdbc:mysql://127.0.0.1:$TEST_PORT/$TEST_DB_SCHEMA
DATASOURCE_MAIN_USERNAME=root
DATASOURCE_MAIN_PASSWORD=mysql
" > "$MYSQL_ENV_FILE"
}

if [ "$COMMAND" = "up" ]; then

  # mysql docker container 가 존재하지 않는다면 생성
  CONTAINER_NAME=$(docker ps -aq -f "name=$HOMEPARTY_DOCKER_NAME")
  if [ -z "$CONTAINER_NAME" -o "$CONTAINER_NAME" = " " ]; then
    echo "up test mysql docker image"
    docker run -d \
      -p $TEST_PORT:3306 \
      -e MYSQL_ROOT_PASSWORD=mysql \
      --name $HOMEPARTY_DOCKER_NAME \
      --platform linux/amd64 \
      --health-cmd='mysqladmin ping --silent' \
      mysql:8.0 \
      --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci
  fi

  # docker 가 띄워져 있는지 체크
  while ! docker exec $HOMEPARTY_DOCKER_NAME mysqladmin -u root -pmysql -h "127.0.0.1" ping --silent &> /dev/null ; do
      IS_STARTED_CONTAINER=$(docker ps -q -f "name=$HOMEPARTY_DOCKER_NAME")
      if [ -z "$IS_STARTED_CONTAINER" -o "$IS_STARTED_CONTAINER" = " " ]; then
        echo "$CONTAINER_NAME restarted..."
        docker restart $CONTAINER_NAME
      fi

      echo "Waiting for database connection..."
      sleep 2
  done

  # main schema 가 존재하지 않는다면 생성
  CHECK_SCHEMA="SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '$MAIN_DB_SCHEMA';"
  RESULT=$(docker exec $HOMEPARTY_DOCKER_NAME /bin/sh -c "mysql -u root -pmysql mysql -e \"$CHECK_SCHEMA\"")

  if [[ -z $RESULT ]]; then
    echo "create main schema..."
    CREATE_DB_QUERY="CREATE SCHEMA $MAIN_DB_SCHEMA CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    docker exec $HOMEPARTY_DOCKER_NAME /bin/sh -c "mysql -u root -pmysql mysql -e \"$CREATE_DB_QUERY\""
  else
    echo "main schema already existed..."
  fi

  # test main schema 가 존재하지 않는다면 생성
  CHECK_SCHEMA="SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '$TEST_DB_SCHEMA';"
  RESULT=$(docker exec $HOMEPARTY_DOCKER_NAME /bin/sh -c "mysql -u root -pmysql mysql -e \"$CHECK_SCHEMA\"")

  if [[ -z $RESULT ]]; then
    echo "create test main schema..."
    CREATE_DB_QUERY="CREATE SCHEMA $TEST_DB_SCHEMA CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    docker exec $HOMEPARTY_DOCKER_NAME /bin/sh -c "mysql -u root -pmysql mysql -e \"$CREATE_DB_QUERY\""
  else
    echo "test main schema already existed..."
  fi

  # create env file
  export-mysql-env

elif [ "$COMMAND" = "down" ]; then
  echo "remove test mysql container..."
  docker stop $HOMEPARTY_DOCKER_NAME
  docker rm $HOMEPARTY_DOCKER_NAME
else
  echo "please check command !! (up or down)"
  exit 1
fi
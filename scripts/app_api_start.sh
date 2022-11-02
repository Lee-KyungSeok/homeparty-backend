#!/bin/bash

env_path=""

for ARGUMENT in "$@"
do
    KEY=$(echo $ARGUMENT | cut -f1 -d=)
    VALUE=$(echo $ARGUMENT | cut -f2 -d=)

    case "$KEY" in
            env_path)           env_path=${VALUE} ;;
            *)
    esac
done

# env_path
if [ -z "$env_path" ]; then
  SCRIPT_REAL=$(realpath -s "$0")
  SCRIPT_DIR=$(dirname "$SCRIPT_REAL")
  env_path=$SCRIPT_DIR/../.env
  echo "cannot find env path, apply default env path: $env_path"
fi

./gradlew build
if [ $? != "0" ]; then
  exit 1;
fi

export $(grep -v '^#' $env_path | xargs)

java -jar ./app-api/build/libs/app-api-0.1.0.jar
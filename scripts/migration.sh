#!/bin/bash

# 만들어야 할 스크립트
# 2. run migration with env (flyway)
# 3. clean migration with env  (flyway)
# 4. add seed with env  (flyway)
# 5. reset (clean + start + seed)  (flyway)

SCRIPT_REAL=$(realpath -s "$0")
SCRIPT_DIR=$(dirname "$SCRIPT_REAL")

COMMAND_LIST=( "gen" "run" "revert" "clean" "seed" "reset" )
DOMAIN_LIST=( "identity" )

help() {
  cat <<EOF

Usage migration.sh COMMAND [OPTIONS]

Commands
  gen           migration 파일 생성 (format: V
  run           migration 실행
  revert        migration 하나 회귀 (개발 필요함)
  clean         migration 초기화
  seed          seed migration 실행
  reset         clean + start + seed 순차적으로 실행

Options
  -b            ([Optional] gen, run, clean, seed, reset) command 실행 도메인 설정. 이 설정값이 있다면 -d 옵션 무시(default: all)
  -d            ([Optional] gen) migration 파일 생성 위치 설정. (default: app root)
  -f            ([Optional] gen) fileFormat 설정. (default: V\${TIMESTAMP}_\${FILENAME})
  -n            ([Required] gen) migration name 설정
  -c            ([Optional] run, clean, seed) flyway configuration 설정. 지정 없을시 test db 동시 실행 (default: 각 디렉터리의 flyway_main.conf)
  -e            ([Optional] all commands) env 파일 path 설정. (default: root .env.migrate 파일)

Sample Commands
  # generate
  $ ./scripts/migration.sh gen -n CreateIdentity -b identity
  $ ./scripts/migration.sh gen -n CreateIdentity -d ./identity/src/main/resources/db/migration
  $ ./scripts/migration.sh gen -n AddIdentityS -d ./identity/src/main/resources/db/seed

  # run
  $ ./scripts/migration.sh run
  $ ./scripts/migration.sh run -b identity

  # clean
  $ ./scripts/migration.sh clean
  $ ./scripts/migration.sh clean -b identity

  # seed
  $ ./scripts/migration.sh seed
  $ ./scripts/migration.sh seed -b identity
  $ ./scripts/migration.sh seed -b identity -c ./flyway.conf

  # reset
  $ ./scripts/migration.sh reset
  $ ./scripts/migration.sh reset -b identity
EOF

  exit 0
}

function contains() {
   local e match="$1"
   shift
   for e; do [[ "$e" == "$match" ]] && return 0; done
   return 1
}

command=$1
shift 1

contains "$command" "${COMMAND_LIST[@]}"
if [ $? == "1" ]; then
    echo "command is wrong please check commands"
    help
    exit 1
fi


while getopts "b:d:f:n:c:e:h" opt
do
  case $opt in
  b) domain=$OPTARG
    domain=$(echo "$domain" | tr A-Z a-z)
    ;;
  d) directory=$OPTARG
    ;;
  f) file_format=$OPTARG
    ;;
  n) migration_name=$OPTARG
    ;;
  c) migration_config=$OPTARG
    ;;
  e) env_path=$OPTARG
    ;;
  h) help ;;
  ?) help ;;
  esac
done
shift $[ $OPTIND - 1 ]

if [ -z "$env_path" ]; then
  env_path=$SCRIPT_DIR/../.env.migrate
  echo "apply default env path: $env_path"
  echo ""

  export $(grep -v '^#' $env_path | xargs)
fi

if [ -z "$domain" ]; then
  domain=all
fi

function generate_migration() {
  if [ -z "$migration_name" ]; then
      echo "migration name should be defined to generate migration file"
      exit 1
  fi

  if [ -z "$file_format" ]; then
    file_format="V$(date +%s000)"
  fi

  if [ $domain == "all" ]; then
    if [ -z "$directory" ]; then
        directory=./
    fi
    if [ "${directory: -1}" != "/" ]; then
      directory="${directory}/"
    fi
  else
    directory=$SCRIPT_DIR/.././identity/src/main/resources/db/migration/
  fi

  migration_file="${directory}${file_format}__${migration_name}.sql"

  echo "generate migration file: $migration_file..."

  mkdir -p "${directory}"
  touch "$migration_file"

  echo ""
}

function migrate_flyway() {
  migration_command=$1
  migration_domain=$2
  migration_file_type=$3 # seed or migration
  migration_domain_upper=$(echo "${migration_domain}" | tr a-z A-Z)
  echo "${migration_command} ${migration_domain} domain"

  if [ -z "$migration_config" ]; then
    export ${migration_domain_upper}_MIGRATION_USERNAME=${DATABASE_USERNAME}
    export ${migration_domain_upper}_MIGRATION_PASSWORD=${DATABASE_PASSWORD}
    export ${migration_domain_upper}_MIGRATION_LOCATION=filesystem:${migration_domain}/src/main/resources/db/${migration_file_type}
    export ${migration_domain_upper}_MIGRATION_CLEAN_DISABLED=false

    # migrate local db
    export ${migration_domain_upper}_MIGRATION_JDBC_URL=jdbc:${DATABASE_TYPE}://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}
    flyway -configFiles=./${migration_domain}/src/main/resources/db/flyway_main.conf $migration_command

    # migrate local test db
    export ${migration_domain_upper}_MIGRATION_JDBC_URL=jdbc:${DATABASE_TYPE}://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}_test
    flyway -configFiles=./${migration_domain}/src/main/resources/db/flyway_main.conf $migration_command
  else
    flyway -configFiles=${migration_config} migrate
  fi
}

if [ $command == "gen" ]; then
  generate_migration
elif [ $command == "run" ]; then
  echo "migrate start..."
  if [ $domain == "all" ]; then
    for DOMAIN in "${DOMAIN_LIST[@]}"; do
        migrate_flyway migrate "$DOMAIN" migration
    done
  else
    migrate_flyway migrate $domain
  fi
elif [ $command == "clean" ]; then
  echo "migrate clean..."
  if [ $domain == "all" ]; then
    for DOMAIN in "${DOMAIN_LIST[@]}"; do
        migrate_flyway clean "$DOMAIN" migration
    done
  else
    migrate_flyway clean $domain
  fi
elif [ $command == "seed" ]; then
  echo "migrate seed..."
  if [ $domain == "all" ]; then
    for DOMAIN in "${DOMAIN_LIST[@]}"; do
        migrate_flyway migrate "$DOMAIN" seed
    done
  else
    migrate_flyway migrate $domain
  fi
elif [ $command == "reset" ]; then
  echo "migrate reset..."
  if [ $domain == "all" ]; then
    for DOMAIN in "${DOMAIN_LIST[@]}"; do
      migrate_flyway clean "$DOMAIN" migration
      migrate_flyway migrate "$DOMAIN" migration
      migrate_flyway migrate "$DOMAIN" seed
    done
  else
    migrate_flyway clean "$domain" migration
    migrate_flyway migrate "$domain" migration
    migrate_flyway migrate "$domain" seed
  fi
else
  echo "command is wrong please check commands :("
  help
fi

echo ""
echo "migration scripts success!! Happy Hacking ~ 😀 😀"
echo ""
#echo "check var"
#echo "domain: $domain"
#echo "directory: $directory"
#echo "file_format: $file_format"
#echo "migration_name: $migration_name"
#echo "env_path: $env_path"
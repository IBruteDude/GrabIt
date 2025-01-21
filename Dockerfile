# FROM openjdk:24-ea-23-slim-bullseye AS builder

# # Install Maven and other required dependencies
# RUN apt-get update && apt-get install -y \
#     wget \
#     unzip \
#     curl \
#     git \
#     maven \
#     && rm -rf /var/lib/apt/lists/*

# WORKDIR /grabit

# COPY . /grabit

# CMD ["mvn", "clean", "package"]


FROM openjdk:24-ea-23-slim-bullseye AS runner

WORKDIR /grabit

EXPOSE 8443



ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/grabit_db
# ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/student_db?createDatabaseIfNotExist=true&amp;allowPublicKeyRetrieval=true&amp;useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

ENV LIQUIBASE_VERSION=4.21.0
ENV LIQUIBASE_CLASSPATH=/grabit/BOOT-INF/lib/mysql-connector-j-8.0.33.jar
ENV LIQUIBASE_DRIVER=com.mysql.cj.jdbc.Driver
ENV LIQUIBASE_COMMAND_URL=${SPRING_DATASOURCE_URL}
ENV LIQUIBASE_COMMAND_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV LIQUIBASE_COMMAND_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV LIQUIBASE_COMMAND_CHANGELOG_FILE=BOOT-INF/classes/db/changelog/db.changelog-master.yaml

ENTRYPOINT ["bash", "./docker-entrypoint.sh"]
COPY docker-entrypoint.sh .
RUN chmod +x docker-entrypoint.sh

## Uncomment if you want it to download the liquibase-cli
# RUN apt-get update
# RUN apt-get install -y wget unzip
# ENV PATH="/opt/liquibase-${LIQUIBASE_VERSION}/liquibase:${PATH}"
# RUN wget "https://github.com/liquibase/liquibase/releases/download/v${LIQUIBASE_VERSION}/liquibase-${LIQUIBASE_VERSION}.zip"
# RUN unzip "liquibase-${LIQUIBASE_VERSION}.zip -d /opt"
# RUN rm "liquibase-${LIQUIBASE_VERSION}.zip"

## Ensure you have liquibase cli downloaded at "./liquibase-cli"
COPY ./liquibase-cli .

COPY target/grabit-0.0.1-SNAPSHOT.jar grabit.jar
RUN jar xf grabit.jar

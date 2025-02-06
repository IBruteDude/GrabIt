FROM openjdk:24-ea-21-slim AS runner

WORKDIR /grabit

EXPOSE 8443


ENV PRODUCTION_DATABASE_URL='jdbc:mysql://mysql:3306/grabit_prod_db?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC'

ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=${PRODUCTION_DATABASE_URL}
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root

ENTRYPOINT ["bash", "./docker-entrypoint.sh"]
COPY docker-entrypoint.sh .
RUN chmod +x docker-entrypoint.sh

COPY target/grabit-0.0.1-SNAPSHOT.jar grabit.jar
RUN jar xf grabit.jar

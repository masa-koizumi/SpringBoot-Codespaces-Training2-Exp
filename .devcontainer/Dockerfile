FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

USER root

RUN apt-get update \
 && apt-get install -y maven postgresql-client \
 && apt-get clean

USER vscode

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/minha-aplicacao.jar /app/minha-aplicacao.jar

EXPOSE 10000

# Etapa 5: Definir o comando para rodar a aplicação
CMD ["java", "-jar", "/app/minha-aplicacao.jar"]

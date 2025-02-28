# Etapa de construção
FROM ubuntu:latest AS build

# Instalar dependências
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Copiar o código-fonte para a imagem
COPY . .

# Compilar o projeto
RUN mvn clean install

# Etapa de execução
FROM ubuntu:latest

# Instalar Java Runtime
RUN apt-get update && \
    apt-get install -y openjdk-17-jre

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da etapa de construção
COPY --from=build /target/todolist-1.0.0.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]

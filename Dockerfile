# Estabelece a imagem base do Java a ser utilizada como openjdk:17-jdk-alpine
FROM openjdk:17-jdk-alpine

# Define o argumento do arquivo JAR com o nome de "/build/libs/gerenciamento_pessoa-endereco-1.0.0.jar"
ARG JAR_FILE=build/libs/gerenciamento_pessoa-endereco-1.0.0.jar

# Copia o arquivo JAR para a imagem com o nome de "app.jar"
COPY ${JAR_FILE} app.jar

# Define o ponto de entrada para a execução da imagem como o comando "java -jar /app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]
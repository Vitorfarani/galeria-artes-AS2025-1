# Usar uma imagem base do Java com JDK
FROM eclipse-temurin:21-jdk

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR para dentro do container
COPY target/artes-0.0.1-SNAPSHOT.jar /app/artes.jar

# Definir a porta que o Azure espera (80)
ENV PORT=80

# Expor a porta para o ambiente externo
EXPOSE 80

# Comando para iniciar a aplicação, usando a porta definida no Azure
ENTRYPOINT ["java", "-Dserver.port=80", "-jar", "artes.jar"]

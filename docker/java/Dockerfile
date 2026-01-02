# Estágio 1: Build da aplicação
# Usando Eclipse Temurin 21, que é a substituta recomendada
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copiar os arquivos do Maven Wrapper e o pom.xml primeiro
# Isso ajuda o Docker a cachear as dependências se o pom.xml não mudar
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copiar o código fonte e gerar o build
COPY src ./src
RUN ./mvnw clean install -DskipTests

# ... (estágio builder acima permanece igual)

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# O segredo está aqui: usamos o curinga * para pegar qualquer JAR gerado no target
# e garantimos que o nome final seja user-api.jar
COPY --from=builder /app/target/*.jar /app/user-api.jar

EXPOSE 8080

# Comando para rodar
ENTRYPOINT ["java", "-jar", "user-api.jar"]
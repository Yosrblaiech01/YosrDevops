# Image de base (version fixée pour plus de stabilité)
FROM alpine:3.18

# Installer seulement le JRE (plus léger que le JDK complet)
RUN apk add --no-cache openjdk11-jre-headless

# Définir un dossier de travail dans le conteneur
WORKDIR /app

# Copier le jar généré par Maven/Gradle dans l'image
COPY target/*.jar app.jar

# Exposer le port utilisé par ton application
EXPOSE 8080

# Commande de démarrage du conteneur
ENTRYPOINT ["java", "-jar", "app.jar"]

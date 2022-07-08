FROM openjdk:17-oracle

RUN microdnf upgrade && microdnf remove expat fontconfig freetype \
  aajohan-comfortaa-fonts fontpackages-filesystem gzip bzip2 tar libpng \
  binutils && microdnf clean all

COPY target/file-transfer.jar /app/

WORKDIR /app

CMD ["java", "-jar", "file-transfer.jar"]

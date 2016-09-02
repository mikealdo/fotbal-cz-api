FROM java:8
VOLUME /tmp
ADD https://bintray.com/mdavidek1/rango/download_file?file_path=cz%2Fmikealdo%2Ffotbal-cz-api%2FCD-20160901225216%2Ffotbal-cz-api-CD-20160901225216.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
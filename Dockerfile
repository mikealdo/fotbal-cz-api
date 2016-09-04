FROM java:8
ENV APP_ENV prod
VOLUME /tmp
ADD 'https://bintray.com/mdavidek1/rango/download_file?file_path=cz%2Fmikealdo%2Ffotbal-cz-api%2FCD-20160901225216%2Ffotbal-cz-api-CD-20160901225216.jar' app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=dev,springCloud","-jar","/app.jar"]
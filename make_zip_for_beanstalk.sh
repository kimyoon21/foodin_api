mv api/build/libs/api-*.jar application.jar
rm application.zip
rm application.jar
zip -r application.zip application.jar .ebextensions

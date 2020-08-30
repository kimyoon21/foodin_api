rm application.zip
mv api/build/libs/api-*.jar application.jar
zip -r application.zip application.jar .ebextensions
rm application.jar

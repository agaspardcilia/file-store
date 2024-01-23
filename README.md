# file-store

### How do I run tests?
To execute both unit and integration tests, run:
```shell
./mvnw verify
```
or
```shell
mvn verify
```

### How do I start the application?
First you need to set up the application properties.
To do so, you can go to `src/main/resources/application.yml` and uncomment the `app` block. After this, you will need
to fill the `directory` property with the path of the directory where you files will be stored. Make sure the application
has rights to access the directory.

Once this is done you can package the project by running:
````shell
mvn package
````
This should have created a jar file in the `target` directory at the root of the project. The file should be named `filestore-0.0.1-SNAPSHOT.jar`.

You can then run the following to start the application:
```shell
java -jar target/filestore-0.0.1-SNAPSHOT.jar
```

The default port is `8080`.

# Java version
Note that this was developed using Java 20 but was later tested on Java 17.






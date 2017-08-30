AnkiWeb-packages to SQL-file converter
======================================


Console application gets questions & answers from AnkiWeb packages & saves it into the SQL-file.


INSTALLATION
------------

Please download & unpack zip-file. You shall see the following files and directories:

| Element |Description |
| ------ | ------ |
| src/ |source-code|
|.gitignore|file with list of ignored files for uploading to the git|
|pom.xml|Maven's project object model file|
|README.md|this file|


REQUIREMENTS
------------

The minimum requirement by project is that you are using [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). 

QUICK START
-----------

1. You should import this project in your IDE as a maven-project;
2. Wait untill IDE will import all maven dependencies to this project;
3. Compile to 'ankitosql.jar';
4. Start 'ankitosql.jar' in the console:

```
    java -jar ankitosql.jar AnkiPakageFile.apkg
```

-----------

***Enjoy***


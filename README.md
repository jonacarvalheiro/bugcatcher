# bugcatcher

## Create jar with all dependencies:

mvn clean compile assembly:single

## Install the .jar on the m2 repository:

mvn install:install-file -Dfile=bugcatcher-1.0-SNAPSHOT.jar -DgroupId=QAE -DartifactId=bugcatcher -Dversion=1.0-SNAPSHOT -Dpackaging=jar

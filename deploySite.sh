#to deploy documentation for a module just add path to the pom as it: -f spring-cache/pom.xml
mvn clean site site:stage scm-publish:publish-scm $@

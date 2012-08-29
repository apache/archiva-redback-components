mvn clean site-deploy -f redback-components-parent/pom.xml -Pall  &&  mvn scm-publish:publish-scm  -f redback-components-parent/pom.xml 

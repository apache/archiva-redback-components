package org.apache.archiva.redback.components.registry.modello;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.codehaus.modello.AbstractModelloGeneratorTest;
import org.codehaus.modello.ModelloException;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.core.ModelloCore;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelValidationException;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.ReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractRegistryGeneratorTestCase
    extends AbstractModelloGeneratorTest
{
    public AbstractRegistryGeneratorTestCase( String name )
    {
        super( name );
    }

    protected void prepareTest( String outputType )
        throws ComponentLookupException, ModelloException, ModelValidationException, IOException, CompilerException
    {
        ModelloCore modello = (ModelloCore) container.lookup( ModelloCore.ROLE );

        Model model = modello.loadModel( ReaderFactory.newXmlReader( getTestFile( "src/test/resources/model.mdo" ) ) );

        File generatedSources = getTestFile( "target/" + outputType + "/sources" );

        File classes = getTestFile( "target/" + outputType + "/classes" );

        FileUtils.deleteDirectory( generatedSources );

        FileUtils.deleteDirectory( classes );

        generatedSources.mkdirs();

        classes.mkdirs();

        Properties parameters = new Properties();

        parameters.setProperty( ModelloParameterConstants.OUTPUT_DIRECTORY, generatedSources.getAbsolutePath() );

        parameters.setProperty( ModelloParameterConstants.VERSION, "1.0.0" );

        parameters.setProperty( ModelloParameterConstants.PACKAGE_WITH_VERSION, Boolean.toString( false ) );

        modello.generate( model, "java", parameters );

        modello.generate( model, outputType, parameters );

        String registryVersion = System.getProperty( "registryVersion" );

        addDependency( "org.codehaus.modello", "modello-core", getModelloVersion() );
        addDependency( "org.apache.archiva.redback.components.registry", "spring-registry-api", registryVersion );
        addDependency( "org.apache.archiva.redback.components.registry", "spring-registry-commons", registryVersion );
        addDependency( "org.codehaus.plexus", "plexus-container-default", "1.0-alpha-33" );
        addDependency( "commons-collections", "commons-collections", "3.1" );
        addDependency( "commons-configuration", "commons-configuration", "1.3" );
        addDependency( "commons-lang", "commons-lang", "2.1" );
        addDependency( "commons-logging", "commons-logging-api", "1.0.4" );
        addDependency( "org.codehaus.plexus", "plexus-interpolation", "1.14" );
        addDependency( "org.slf4j", "slf4j-api", System.getProperty( "slf4jVersion" ) );

        if ( "1.5".compareTo( System.getProperty( "java.specification.version" ) ) <= 0 )
        {
            // causes a conflict with JDK 1.4 => add this dependency only with JDK 1.5+
            addDependency( "xerces", "xercesImpl", "2.9.1" );
        }

        compile( generatedSources, classes );
    }
}

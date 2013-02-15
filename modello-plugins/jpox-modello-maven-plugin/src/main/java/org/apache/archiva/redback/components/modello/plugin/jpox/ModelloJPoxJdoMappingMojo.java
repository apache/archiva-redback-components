package org.apache.archiva.redback.components.modello.plugin.jpox;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.maven.AbstractModelloGeneratorMojo;

import java.io.File;
import java.util.Properties;

/**
 * Creates a JDO mapping from the Modello model.
 *
 * @author Olivier Lamy
 */
@Mojo( name = "jpox-jdo-mapping", defaultPhase = LifecyclePhase.GENERATE_RESOURCES )
public class ModelloJPoxJdoMappingMojo
    extends AbstractModelloGeneratorMojo
{
    /**
     * The output directory of the generated JDO mapping file.
     * <p/>
     * Please be aware of the &lt;model jpox.mapping-in-package="true" &gt; attribute present in
     * the model file itself.
     */
    @Parameter( defaultValue = "${basedir}/target/generated-resources/modello", required = true )
    private File outputDirectory;

    /**
     * Produce a mapping file suitable for replication. It will have an alternate extension of '.jdorepl' so it is not
     * picked up by default, and all value-strategy and objectid-class entries are removed from the mapping so that
     * the original identities can be used.
     */
    @Parameter( defaultValue = "false" )
    private boolean replicationParameters;

    protected String getGeneratorType()
    {
        return "jpox-jdo-mapping";
    }

    protected boolean producesCompilableResult()
    {
        return false;
    }

    protected boolean producesResources()
    {
        return true;
    }

    public File getOutputDirectory()
    {
        return outputDirectory;
    }

    public void setOutputDirectory( File outputDirectory )
    {
        this.outputDirectory = outputDirectory;
    }

    protected void customizeParameters( Properties parameters )
    {
        super.customizeParameters( parameters );

        if ( replicationParameters )
        {
            parameters.setProperty( ModelloParameterConstants.FILENAME, "package.jdorepl" );

            parameters.setProperty( "JPOX.override.value-strategy", "off" );
            parameters.setProperty( "JPOX.override.objectid-class", "" );
        }
    }
}
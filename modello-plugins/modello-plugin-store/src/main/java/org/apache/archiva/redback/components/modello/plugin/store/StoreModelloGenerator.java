package org.apache.archiva.redback.components.modello.plugin.store;

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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.codehaus.modello.ModelloException;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.plugin.AbstractModelloGenerator;
import org.apache.archiva.redback.components.modello.plugin.store.metadata.StoreClassMetadata;
import org.codehaus.plexus.util.WriterFactory;
import org.codehaus.plexus.velocity.VelocityComponent;

import java.io.File;
import java.io.Writer;
import java.util.Properties;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: StoreModelloGenerator.java 895 2008-05-24 22:31:50Z hboutemy $
 */
public class StoreModelloGenerator
    extends AbstractModelloGenerator
{
    /** @requirement */
    private VelocityComponent velocity;

    public void generate( Model model, Properties properties )
        throws ModelloException
    {
        initialize( model, properties );


        // ----------------------------------------------------------------------
        // Initialize the Velocity context
        // ----------------------------------------------------------------------

        Context context = new VelocityContext();

        context.put( "version", getGeneratedVersion() );

        context.put( "package", model.getDefaultPackageName( false, getGeneratedVersion() ) );

        context.put( "metadataId", StoreClassMetadata.ID );

        context.put( "model", model );

        // ----------------------------------------------------------------------
        // Generate the code
        // ----------------------------------------------------------------------

        String packageName = model.getDefaultPackageName( false, getGeneratedVersion() );

        File packageFile = new File( getOutputDirectory(), packageName.replace( '.', File.separatorChar ) );

        File interfaceFile = new File( packageFile, model.getName() + "Store.java" );

        File exceptionFile = new File( packageFile, model.getName() + "StoreException.java" );

        if ( !interfaceFile.getParentFile().exists() )
        {
            if ( !interfaceFile.getParentFile().mkdirs() )
            {
                throw new ModelloException( "Error while creating parent directories for '" + interfaceFile.getAbsolutePath() + "'." );
            }
        }

        String interfaceTemplate = "org/apache/archiva/redback/components/modello/plugin/store/templates/Store.vm";

        String exceptionTemplate =
            "org/apache/archiva/redback/components/modello/plugin/store/templates/StoreException.vm";

        writeTemplate( interfaceTemplate, interfaceFile, context );

        writeTemplate( exceptionTemplate, exceptionFile, context );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private void writeTemplate( String template, File file, Context context )
        throws ModelloException
    {
        try
        {
            Writer writer = getEncoding() == null ? WriterFactory.newPlatformWriter( file )
                                : WriterFactory.newWriter( file, getEncoding() );

            velocity.getEngine().mergeTemplate( template, context, writer );

            writer.flush();

            writer.close();
        }
        catch ( Exception e )
        {
            throw new ModelloException( "Error while generating code.", e );
        }
    }
}

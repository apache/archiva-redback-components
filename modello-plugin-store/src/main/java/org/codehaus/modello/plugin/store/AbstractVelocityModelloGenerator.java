package org.codehaus.modello.plugin.store;

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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.codehaus.modello.ModelloException;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.Version;
import org.codehaus.modello.plugin.AbstractModelloGenerator;
import org.codehaus.modello.plugin.store.tool.JavaTool;
import org.codehaus.plexus.util.WriterFactory;
import org.codehaus.plexus.velocity.VelocityComponent;

import java.io.File;
import java.util.HashMap;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: AbstractVelocityModelloGenerator.java 901 2008-05-26 21:25:01Z hboutemy $
 */
public abstract class AbstractVelocityModelloGenerator
    extends AbstractModelloGenerator
{
    /**
     * @requirement
     */
    private VelocityComponent velocity;

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    protected static Context makeStubVelocityContext( Model model, Version version )
    {
        String packageName = getGeneratedPackage( model, version );

        return makeStubVelocityContext( model, version, packageName );
    }

    private static String getGeneratedPackage( Model model, Version version )
    {
        return model.getDefaultPackageName( false, version );
    }

    protected static Context makeStubVelocityContext( Model model, Version version, String packageName )
    {
        List classes = model.getClasses( version );

        Map classFields = new HashMap();

        for ( Iterator it = classes.iterator(); it.hasNext(); )
        {
            ModelClass modelClass = (ModelClass) it.next();

            List fields = modelClass.getFields( version );

            classFields.put( modelClass.getName(), fields );
        }

        Context context = new VelocityContext();

        context.put( "version", version );

        context.put( "model", model );

        context.put( "classes", classes );

        context.put( "classFields", classFields );

        context.put( "javaTool", new JavaTool() );

        context.put( "package", packageName );

        return context;
    }

    protected void writeClass( String templateName, File basedir, String packageName, String className,
                               Context context )
        throws ModelloException
    {
        File packageFile = new File( getOutputDirectory(), packageName.replace( '.', File.separatorChar ) );

        File file = new File( packageFile, className + ".java" );

        writeTemplate( templateName, file, context );
    }

    protected void writeTemplate( String templateName, File file, Context context )
        throws ModelloException
    {
        Template template = getTemplate( templateName );

        if ( template == null )
        {
            ClassLoader old = Thread.currentThread().getContextClassLoader();

            try
            {
                Thread.currentThread().setContextClassLoader( this.getClass().getClassLoader() );

                template = getTemplate( templateName );
            }
            finally
            {
                Thread.currentThread().setContextClassLoader( old );
            }
        }

        if ( template == null )
        {
            throw new ModelloException( "Could not find the template '" + templateName + "'." );
        }

        if ( !file.getParentFile().exists() )
        {
            if ( !file.getParentFile().mkdirs() )
            {
                throw new ModelloException(
                    "Error while creating parent directories for '" + file.getAbsolutePath() + "'." );
            }
        }

        try
        {
            Writer writer = getEncoding() == null ? WriterFactory.newPlatformWriter( file )
                            : WriterFactory.newWriter( file, getEncoding() );

            template.merge( context, writer );

            writer.close();
        }
        catch ( Exception e )
        {
            throw new ModelloException( "Error while generating code.", e );
        }
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private Template getTemplate( String name )
        throws ModelloException
    {
        try
        {
            return velocity.getEngine().getTemplate( name );
        }
        catch ( ResourceNotFoundException e )
        {
            return null;
        }
        catch ( ParseErrorException e )
        {
            throw new ModelloException( "Could not parse the template '" + name + "'.", e );
        }
        catch ( Exception e )
        {
            throw new ModelloException( "Error while loading template '" + name + "'.", e );
        }
    }
}

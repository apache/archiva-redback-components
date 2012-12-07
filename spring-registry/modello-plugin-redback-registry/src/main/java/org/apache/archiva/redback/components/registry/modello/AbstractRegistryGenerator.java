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

import org.apache.velocity.context.Context;
import org.codehaus.modello.ModelloException;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.plugin.store.AbstractVelocityModelloGenerator;

import java.util.Properties;

public abstract class AbstractRegistryGenerator
    extends AbstractVelocityModelloGenerator
{
    protected void generate( Model model, Properties parameters, String outputType )
        throws ModelloException
    {
        initialize( model, parameters );

        // Initialize the Velocity context

        String packageName;
        if ( isPackageWithVersion() )
        {
            packageName = model.getDefaultPackageName( true, getGeneratedVersion() );
        }
        else
        {
            packageName = model.getDefaultPackageName( false, null );
        }

        packageName += ".io.registry";

        Context context = makeStubVelocityContext( model, getGeneratedVersion(), packageName );


        // Generate the reader
        String className = model.getName() + outputType;

        writeClass( getClass().getPackage().getName().replaceAll( "\\.", "/" ) + "/" + outputType + ".java.vm",
                    getOutputDirectory(), packageName, className, context );
    }
}

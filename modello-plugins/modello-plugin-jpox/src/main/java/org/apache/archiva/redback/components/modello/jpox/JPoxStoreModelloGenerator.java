package org.apache.archiva.redback.components.modello.jpox;

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
import org.codehaus.modello.plugin.store.metadata.StoreClassMetadata;
import org.codehaus.modello.plugin.store.metadata.StoreFieldMetadata;

import java.util.Properties;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: JPoxStoreModelloGenerator.java 713 2006-11-25 21:58:06Z jvanzyl $
 */
public class JPoxStoreModelloGenerator
    extends AbstractVelocityModelloGenerator
{
    public void generate( Model model, Properties parameters )
        throws ModelloException
    {
        initialize( model, parameters );

        // Initialize the Velocity context

        Context context = makeStubVelocityContext( model, getGeneratedVersion() );

        context.put( "storeClassMetadataId", StoreClassMetadata.ID );

        context.put( "storeFieldMetadataId", StoreFieldMetadata.ID );

        // Generate a ModelloMetadata class for storing model information in the database
        String packageName = model.getDefaultPackageName( isPackageWithVersion(), getGeneratedVersion() );

        // Generate the JPoxStore
        String className = model.getName() + "JPoxStore";

        writeClass( "org/apache/archiva/redback/components/modello/jpox/templates/JPoxStore.java.vm", getOutputDirectory(), packageName,
                    className, context );
    }
}

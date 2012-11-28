package org.apache.archiva.redback.components.modello.plugin.store.metadata;

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

import org.codehaus.modello.ModelloException;
import org.codehaus.modello.metadata.AbstractMetadataPlugin;
import org.codehaus.modello.metadata.AssociationMetadata;
import org.codehaus.modello.metadata.ClassMetadata;
import org.codehaus.modello.metadata.FieldMetadata;
import org.codehaus.modello.metadata.InterfaceMetadata;
import org.codehaus.modello.metadata.ModelMetadata;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelAssociation;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.ModelField;
import org.codehaus.modello.model.ModelInterface;
import org.codehaus.plexus.util.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: StoreMetadataPlugin.java 796 2007-02-09 00:10:59Z brett $
 */
public class StoreMetadataPlugin
    extends AbstractMetadataPlugin
{
    public final static String PART = "stash.part";

    public final static String KEY_TYPE = "stash.keyType";

    // ----------------------------------------------------------------------
    // Map to Metadata
    // ----------------------------------------------------------------------

    public ModelMetadata getModelMetadata( Model model, Map data )
    {
        return new StoreModelMetadata();
    }

    public InterfaceMetadata getInterfaceMetadata( ModelInterface modelInterface, Map<String, String> stringStringMap )
        throws ModelloException
    {
        // TODO implements ?
        return null;
    }

    public ClassMetadata getClassMetadata( ModelClass clazz, Map data )
        throws ModelloException
    {
        StoreClassMetadata metadata = new StoreClassMetadata();

        String storable = (String) data.get( "stash.storable" );

        if ( storable != null && storable.equals( "true" ) )
        {
//            JavaClassMetadata jcm = (JavaClassMetadata) clazz.getMetadata( JavaClassMetadata.ID );
//
//            if ( jcm.isAbstract() )
//            {
//                throw new ModelloException( "A storable class can't be abstract. " +
//                                            "Class name '" + clazz.getName() + "'." );
//            }
//
            metadata.setStorable( true );
        }

        return metadata;
    }

    public FieldMetadata getFieldMetadata( ModelField field, Map data )
        throws ModelloException
    {
        StoreFieldMetadata metadata = new StoreFieldMetadata();

        // ----------------------------------------------------------------------
        // Fields are per default storable as the fields can't be persisted
        // unless the class itself is storable.
        // ----------------------------------------------------------------------

        metadata.setStorable( getBoolean( data, "stash.storable", true ) );

        String maxSize = (String) data.get( "stash.maxSize" );

        if ( !StringUtils.isEmpty( maxSize ) )
        {
            if ( !field.getType().equals( "String" ) )
            {
                throw new ModelloException( "When specifying max size on a field the type must be String. " +
                                            "Class: '" + field.getModelClass().getName() + "', " +
                                            "field : '" + field.getName() + "'." );
            }

            try
            {
                metadata.setMaxSize( Integer.parseInt( maxSize ) );
            }
            catch ( NumberFormatException e )
            {
                throw new ModelloException( "Max size on a field the type must be String. " +
                                            "Class: '" + field.getModelClass().getName() + "', " +
                                            "field : '" + field.getName() + "'." );
            }
        }

        return metadata;
    }

    public AssociationMetadata getAssociationMetadata( ModelAssociation association, Map data )
        throws ModelloException
    {
        StoreAssociationMetadata metadata = new StoreAssociationMetadata();

        // ----------------------------------------------------------------------
        // Associations are per default storable as the fields can't be persisted
        // unless the class itself is storable.
        // ----------------------------------------------------------------------

        metadata.setStorable( getBoolean( data, "stash.storable", true ) );

        if ( data.containsKey( PART ) )
        {
            metadata.setPart( Boolean.valueOf( (String) data.get( PART ) ) );
        }

        String keyType = (String) data.get( KEY_TYPE );

        if ( association.getType() != null && association.getType().equals( "Map" ) )
        {
            if ( StringUtils.isEmpty( keyType ) )
            {
                keyType = "String";
            }

            // TODO: assert the key type

            metadata.setKeyType( keyType );
        }

        return metadata;
    }

    // ----------------------------------------------------------------------
    // Metadata to Map
    // ----------------------------------------------------------------------

    public Map getFieldMap( ModelField field, FieldMetadata metadata )
    {
        return Collections.EMPTY_MAP;
    }
}

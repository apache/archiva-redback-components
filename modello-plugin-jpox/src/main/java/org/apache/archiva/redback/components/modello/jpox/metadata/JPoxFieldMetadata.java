package org.apache.archiva.redback.components.modello.jpox.metadata;

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

import org.codehaus.modello.metadata.FieldMetadata;
import org.codehaus.plexus.util.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: JPoxFieldMetadata.java 829 2007-03-22 14:32:42Z joakime $
 */
public class JPoxFieldMetadata implements FieldMetadata
{
    public static final String ID = JPoxFieldMetadata.class.getName();

    public static final String[] FOREIGN_KEY_ACTIONS = new String[] { "cascade", "restrict", "null", "default" };

    public static final String[] BOOLEANS = new String[] { "true", "false" };

    private List fetchGroupNames;

    private String mappedBy;

    private String nullValue;

    private String columnName;

    private boolean primaryKey;

    private String persistenceModifier;

    private String valueStrategy;

    private String joinTableName;

    private String indexed;

    private boolean unique;

    private boolean foreignKey;

    private String foreignKeyDeferred;

    private String foreignKeyDeleteAction;

    private String foreignKeyUpdateAction;

    public List getFetchGroupNames()
    {
        return fetchGroupNames;
    }

    public void setFetchGroupNames( List fetchGroupNames )
    {
        this.fetchGroupNames = fetchGroupNames;
    }

    public String getMappedBy()
    {
        return mappedBy;
    }

    public void setMappedBy( String mappedBy )
    {
        this.mappedBy = mappedBy;
    }

    public String getNullValue()
    {
        return nullValue;
    }

    public void setNullValue( String nullValue )
    {
        this.nullValue = nullValue;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName( String columnName )
    {
        this.columnName = columnName;
    }

    public boolean isPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey( boolean primaryKey )
    {
        this.primaryKey = primaryKey;
    }

    public String getPersistenceModifier()
    {
        return persistenceModifier;
    }

    public void setPersistenceModifier( String persistenceModifier )
    {
        this.persistenceModifier = persistenceModifier;
    }

    public String getValueStrategy()
    {
        return valueStrategy;
    }

    public void setValueStrategy( String valueStrategy )
    {
        this.valueStrategy = valueStrategy;
    }

    public String getJoinTableName()
    {
        return joinTableName;
    }

    public void setJoinTableName( String joinTableName )
    {
        this.joinTableName = joinTableName;
    }

    public String getIndexed()
    {
        return indexed;
    }

    public void setIndexed( String indexed )
    {
        this.indexed = indexed;
    }

    public String getForeignKeyDeferred()
    {
        return foreignKeyDeferred;
    }

    public void setForeignKeyDeferred( String foreignKeyDeferred )
    {
        this.foreignKeyDeferred = foreignKeyDeferred;

        if ( StringUtils.isNotEmpty( this.foreignKeyDeferred ) )
        {
            this.foreignKey = true;
        }
    }

    public String getForeignKeyDeleteAction()
    {
        return foreignKeyDeleteAction;
    }

    public void setForeignKeyDeleteAction( String foreignKeyDeleteAction )
    {
        this.foreignKeyDeleteAction = foreignKeyDeleteAction;

        if ( StringUtils.isNotEmpty( this.foreignKeyDeleteAction ) )
        {
            this.foreignKey = true;
        }
    }

    public String getForeignKeyUpdateAction()
    {
        return foreignKeyUpdateAction;
    }

    public void setForeignKeyUpdateAction( String foreignKeyUpdateAction )
    {
        this.foreignKeyUpdateAction = foreignKeyUpdateAction;

        if ( StringUtils.isNotEmpty( this.foreignKeyUpdateAction ) )
        {
            this.foreignKey = true;
        }
    }

    public boolean isUnique()
    {
        return unique;
    }

    public void setUnique( boolean unique )
    {
        this.unique = unique;
    }

    public boolean isForeignKey()
    {
        return foreignKey;
    }

    public void setForeignKey( boolean foreignKey )
    {
        this.foreignKey = foreignKey;
    }
}

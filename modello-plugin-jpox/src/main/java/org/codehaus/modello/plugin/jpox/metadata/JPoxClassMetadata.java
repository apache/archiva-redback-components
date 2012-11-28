package org.codehaus.modello.plugin.jpox.metadata;

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

import org.codehaus.modello.metadata.ClassMetadata;

import java.util.List;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: JPoxClassMetadata.java 822 2007-03-13 22:30:38Z joakime $
 */
public class JPoxClassMetadata
    implements ClassMetadata
{
    public static final String ID = JPoxClassMetadata.class.getName();

    private boolean enabled;

    private boolean detachable;
    
    private String table;
    
    private String columnPrefix;
    
    private String identityType;
    
    private String identityClass;
    
    private boolean useIdentifiersAsPrimaryKey;
    
    private List notPersisted;

    public void setDetachable( boolean detachable )
    {
        this.detachable = detachable;
    }

    public boolean isDetachable()
    {
        return detachable;
    }

    public void setTable( String table )
    {
        this.table = table;
    }

    public String getTable()
    {
        return table;
    }

    public String getIdentityClass()
    {
        return identityClass;
    }

    public void setIdentityClass( String identityClass )
    {
        this.identityClass = identityClass;
    }

    public String getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType( String identityType )
    {
        this.identityType = identityType;
    }

    public boolean useIdentifiersAsPrimaryKey()
    {
        return useIdentifiersAsPrimaryKey;
    }

    public void setUseIdentifiersAsPrimaryKey( boolean userIdentifiersAsIdentity )
    {
        this.useIdentifiersAsPrimaryKey = userIdentifiersAsIdentity;
    }

    public List getNotPersisted()
    {
        return notPersisted;
    }

    public void setNotPersisted( List notPersisted )
    {
        this.notPersisted = notPersisted;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean persisted )
    {
        this.enabled = persisted;
    }

    public String getColumnPrefix()
    {
        return columnPrefix;
    }

    public void setColumnPrefix( String columnPrefix )
    {
        this.columnPrefix = columnPrefix;
    }
}

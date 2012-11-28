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

import org.codehaus.modello.metadata.ModelMetadata;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: JPoxModelMetadata.java 827 2007-03-21 19:31:37Z joakime $
 */
public class JPoxModelMetadata implements ModelMetadata
{
    public static final String ID = JPoxModelMetadata.class.getName();
    
    public static final String ERROR = "error";
    
    public static final String WARNING = "warning";

    private String columnPrefix;

    private String tablePrefix;
    
    private String reservedWordStrictness;
    
    private boolean mappingInPackage = false;
    
    public String getColumnPrefix()
    {
        return columnPrefix;
    }

    public void setColumnPrefix( String columnPrefix )
    {
        this.columnPrefix = columnPrefix;
    }

    public String getTablePrefix()
    {
        return tablePrefix;
    }

    public void setTablePrefix( String tablePrefix )
    {
        this.tablePrefix = tablePrefix;
    }

    public String getReservedWordStrictness()
    {
        return reservedWordStrictness;
    }

    public void setReservedWordStrictness( String reservedWordStrictness )
    {
        this.reservedWordStrictness = reservedWordStrictness;
    }

    public boolean isMappingInPackage()
    {
        return mappingInPackage;
    }

    public void setMappingInPackage( boolean mappingInPackage )
    {
        this.mappingInPackage = mappingInPackage;
    }
}

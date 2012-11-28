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

import org.codehaus.modello.metadata.FieldMetadata;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id: StoreFieldMetadata.java 243 2005-02-28 13:51:51Z trygvis $
 */
public class StoreFieldMetadata
    implements FieldMetadata
{
    public static final String ID = StoreFieldMetadata.class.getName();

    private boolean storable;

    private int maxSize;

    public void setStorable( boolean storable )
    {
        this.storable = storable;
    }

    public boolean isStorable()
    {
        return storable;
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize( int maxSize )
    {
        this.maxSize = maxSize;
    }
}

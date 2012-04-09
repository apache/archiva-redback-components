package org.apache.archiva.redback.components.apacheds;

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

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import java.util.Set;
import java.util.HashSet;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class Partition
{
    private String name;

    private String suffix;

    private Set indexedAttributes;

    private Attributes contextAttributes;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix( String suffix )
    {
        this.suffix = suffix;
    }

    public Set getIndexedAttributes()
    {
        if ( indexedAttributes == null )
        {
            indexedAttributes = new HashSet();
        }

        return indexedAttributes;
    }

    public void setIndexedAttributes( Set indexedAttributes )
    {
        this.indexedAttributes = indexedAttributes;
    }

    public Attributes getContextAttributes()
    {
        if ( contextAttributes == null )
        {
            contextAttributes = new BasicAttributes();
        }

        return contextAttributes;
    }

    public void setContextAttributes( Attributes contextAttributes )
    {
        this.contextAttributes = contextAttributes;
    }
}

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

import org.codehaus.modello.metadata.AssociationMetadata;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: JPoxAssociationMetadata.java 765 2006-12-27 00:00:20Z aheritier $
 */
public class JPoxAssociationMetadata
    implements AssociationMetadata
{
    public static final String ID = JPoxAssociationMetadata.class.getName();

    private boolean dependent;

    private boolean join;

    public boolean isDependent()
    {
        return dependent;
    }

    public void setDependent( boolean dependent )
    {
        this.dependent = dependent;
    }

    public boolean isJoin()
    {
        return join;
    }

    public void setJoin( boolean join )
    {
        this.join = join;
    }
}

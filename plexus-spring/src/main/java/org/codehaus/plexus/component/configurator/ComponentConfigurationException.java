package org.codehaus.plexus.component.configurator;

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

import org.codehaus.plexus.configuration.PlexusConfiguration;

/**
 *
 * 
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 *
 * @version $Id$
 */
public class ComponentConfigurationException
    extends Exception
{
    private PlexusConfiguration failedConfiguration;

    public ComponentConfigurationException( String message )
    {
        super( message );
    }

    public ComponentConfigurationException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public ComponentConfigurationException( Throwable cause )
    {
        super( cause );
    }
    
    public ComponentConfigurationException( PlexusConfiguration failedConfiguration, String message )
    {
        super( message );
        this.failedConfiguration = failedConfiguration;
    }

    public ComponentConfigurationException( PlexusConfiguration failedConfiguration, String message, Throwable cause )
    {
        super( message, cause );
        this.failedConfiguration = failedConfiguration;
    }

    public ComponentConfigurationException( PlexusConfiguration failedConfiguration, Throwable cause )
    {
        super( cause );
        this.failedConfiguration = failedConfiguration;
    }
    
    public void setFailedConfiguration( PlexusConfiguration failedConfiguration )
    {
        this.failedConfiguration = failedConfiguration;
    }
    
    public PlexusConfiguration getFailedConfiguration()
    {
        return failedConfiguration;
    }
}
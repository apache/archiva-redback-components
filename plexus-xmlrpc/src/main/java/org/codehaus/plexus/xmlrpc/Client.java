package org.codehaus.plexus.xmlrpc;

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
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class Client
{
    /** */
    private String hostname;

    public Client()
    {
    }

    /**
     * @return Returns the hostname.
     */
    public String getHostname()
        throws InitializationException
    {
        if ( StringUtils.isEmpty( hostname ) )
        {
            throw new InitializationException( "The 'hostname' element cannot be empty." );
        }

        // TODO: Add more validation of the IP/hostname itself

        return hostname;
    }
}

package org.codehaus.plexus.jabber;

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

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public interface JabberClient
{
    static final String ROLE = JabberClient.class.getName();

    void connect()
        throws JabberClientException;

    void disconnect()
        throws JabberClientException;

    void logon()
        throws JabberClientException;

    void logoff()
        throws JabberClientException;

    void sendMessageToUser( String recipient, String message )
        throws JabberClientException;

    void sendMessageToGroup( String recipient, String message )
        throws JabberClientException;

    String getHost();

    void setHost( String host );

    int getPort();

    void setPort( int port );

    boolean isSslConnection();

    void setSslConnection( boolean isSslConnection );

    String getUser();

    void setUser( String user );

    void setPassword( String password );

    String getImDomainName();

    void setImDomainName( String imDomainName );
}

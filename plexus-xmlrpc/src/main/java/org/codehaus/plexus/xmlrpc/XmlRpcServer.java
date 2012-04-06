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

import org.apache.xmlrpc.XmlRpcException;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public interface XmlRpcServer
{
    String ROLE = XmlRpcServer.class.getName();

    // ----------------------------------------------------------------------
    // Listener Management
    // ----------------------------------------------------------------------

    void addListener( String host, int port, boolean secure )
        throws XmlRpcException;

    void removeListener( String host, int port )
        throws XmlRpcException;

    void startListener( String host, int port )
        throws XmlRpcException;

    // ----------------------------------------------------------------------
    // Handler Management
    // ----------------------------------------------------------------------

    void addHandler( String name, Object handler )
        throws XmlRpcException;

    void addHandler( String host, String name, int port, Object handler )
        throws XmlRpcException;

    void removeHandler( String name )
        throws XmlRpcException;

    void removeHandler( String host, int port, String name )
        throws XmlRpcException;

    // ----------------------------------------------------------------------
    // Authorization Management
    // ----------------------------------------------------------------------

    void acceptClient( String clientHost );

    void acceptClient( String host, int port, String clientHost );

    void denyClient( String clientHost );

    void denyClient( String host, int port, String clientHost );

    void setParanoid( String host, int port, boolean state );
}

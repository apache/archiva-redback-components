package org.codehaus.modello.plugin.jpox;

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

import java.util.Properties;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import junit.framework.Assert;
import org.apache.log4j.*;
import org.codehaus.modello.verifier.Verifier;
import org.codehaus.plexus.security.authorization.rbac.jdo.RbacJdoModelModelloMetadata;
import org.codehaus.plexus.security.authorization.rbac.jdo.RbacJdoModelJPoxStore;

/**
 * @version $Id: Xpp3Verifier.java 675 2006-11-16 10:58:59Z brett $
 */
public class JpoxVerifierVersion
    extends Verifier
{
    public void verify()
        throws Exception
    {
        Properties properties = new Properties();
        properties.setProperty( "javax.jdo.PersistenceManagerFactoryClass", "org.jpox.PersistenceManagerFactoryImpl" );
        properties.setProperty( "javax.jdo.option.ConnectionDriverName", "org.apache.derby.jdbc.EmbeddedDriver" );
        properties.setProperty( "javax.jdo.option.ConnectionURL", "jdbc:derby:target/jpox-version/database;create=true" );
        properties.setProperty( "javax.jdo.option.ConnectionUserName", "sa" );
        properties.setProperty( "javax.jdo.option.ConnectionPassword", "" );
        properties.setProperty( "org.jpox.autoCreateSchema", "true" );
        properties.setProperty( "org.jpox.validateTables", "false" );
        properties.setProperty( "org.jpox.validateColumns", "false" );
        properties.setProperty( "org.jpox.validateConstraints", "false" );

        PropertyConfigurator.configure( getClass().getResource( "/log4j.properties" ) );
        // Logger.getLogger( "JPOX" ).setLevel( Level.DEBUG );

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory( properties );

        RbacJdoModelJPoxStore store = new RbacJdoModelJPoxStore( pmf );

        RbacJdoModelModelloMetadata metadata = store.getRbacJdoModelModelloMetadata( true );

        Assert.assertNull( metadata );

        metadata = new RbacJdoModelModelloMetadata();
        metadata.setModelVersion( "1.0.0" );
        store.storeRbacJdoModelModelloMetadata( metadata );

        metadata = store.getRbacJdoModelModelloMetadata( true );
        Assert.assertEquals( "1.0.0", metadata.getModelVersion() );
    }
}

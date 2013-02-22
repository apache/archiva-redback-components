package org.apache.archiva.redback.components.jdo;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author David Wynter
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 *
 */
public class DefaultJdoFactory
    implements JdoFactory
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    private static final String CONNECTION_DRIVER_NAME = "javax.jdo.option.ConnectionDriverName";

    /**
     * @configuration
     */
    private Properties properties;

    private PersistenceManagerFactory persistenceManagerFactory;

    public PersistenceManagerFactory getPersistenceManagerFactory()
    {
        return persistenceManagerFactory;
    }

    // ----------------------------------------------------------------------
    // Component Lifecycle
    // ----------------------------------------------------------------------

    @PostConstruct
    public void initialize()
    {
        logger.info( "Initializing JDO." );

        persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory( properties );

        String driverClass = null;

        try
        {
            driverClass = (String) properties.get( CONNECTION_DRIVER_NAME );
            
            if ( driverClass == null )
            {
                throw new RuntimeException( "Property " + CONNECTION_DRIVER_NAME + " was not set in JDO Factory." );
            }

            //TODO: Class.forName is evil
            Thread.currentThread().getContextClassLoader().loadClass( driverClass );
        }
        catch ( ClassNotFoundException e )
        {
            throw new RuntimeException( "Cannot find driver class: " + driverClass, e );
        }
    }

    public void shutdown()
        throws Exception
    {
        dispose();
    }

    @PreDestroy
    public void dispose()
    {
        if ( properties != null )
        {
            String databaseUrl = properties.getProperty( "javax.jdo.option.ConnectionURL" );

            if ( databaseUrl != null )
            {

                if ( databaseUrl.indexOf( "jdbc:derby:" ) == 0 )
                {
                    String databasePath = databaseUrl.substring( "jdbc:derby:".length() );

                    if ( databasePath.indexOf( ';' ) > 0 )
                    {
                        databasePath = databasePath.substring( 0, databasePath.indexOf( ';' ) );
                    }

                    try
                    {
                        /* shutdown the database */
                        DriverManager.getConnection( "jdbc:derby:" + databasePath + ";shutdown=true" );
                    }
                    catch ( SQLException e )
                    {
                        /*
                         * In Derby, any request to the DriverManager with a shutdown=true attribute raises an exception.
                         * http://db.apache.org/derby/manuals/reference/sqlj251.html
                         */
                    }

                    System.gc();
                }
            }
        }
    }
}

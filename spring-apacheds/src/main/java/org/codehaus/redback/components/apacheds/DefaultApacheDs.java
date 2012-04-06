package org.codehaus.redback.components.apacheds;

/*
 * Copyright 2001-2007 The Codehaus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.directory.server.configuration.MutableServerStartupConfiguration;
import org.apache.directory.server.core.configuration.MutablePartitionConfiguration;
import org.apache.directory.server.core.configuration.ShutdownConfiguration;
import org.apache.directory.server.core.configuration.SyncConfiguration;
import org.apache.directory.server.jndi.ServerContextFactory;
import org.apache.directory.server.ldap.LdapConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.InitialDirContext;
import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @author Olivier Lamy
 * @version $Id$
 */
public class DefaultApacheDs
    implements ApacheDs
{

    private Logger logger = LoggerFactory.getLogger( getClass() );

    // ----------------------------------------------------------------------
    // Configuration
    // ----------------------------------------------------------------------

    private boolean enableNetworking;

    private File basedir;

    private int port;

    private String password;

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private boolean stopped;

    private MutableServerStartupConfiguration configuration;

    private Set partitionConfigurations = new HashSet();

    // ----------------------------------------------------------------------
    // ApacheDs Implementation
    // ----------------------------------------------------------------------

    public void setBasedir( File basedir )
    {
        this.basedir = basedir;
    }

    public void setEnableNetworking( boolean enableNetworking )
    {
        this.enableNetworking = enableNetworking;
    }

    public InitialDirContext getAdminContext()
        throws NamingException
    {
        assertIsStarted();

        Hashtable environment = new Hashtable( configuration.toJndiEnvironment() );
        environment.put( Context.INITIAL_CONTEXT_FACTORY, ServerContextFactory.class.getName() );
        environment.put( Context.SECURITY_PRINCIPAL, "uid=admin,ou=system" );
        environment.put( Context.SECURITY_CREDENTIALS, password );
        environment.put( Context.SECURITY_AUTHENTICATION, "simple" );
//        environment.put( Context.PROVIDER_URL, "dc=hauskeeper,dc=codehaus,dc=org" );
        return new InitialDirContext( environment );
    }

    public InitialDirContext getSystemContext()
        throws NamingException
    {
        assertIsStarted();

        Hashtable environment = new Hashtable( configuration.toJndiEnvironment() );
        environment.put( Context.INITIAL_CONTEXT_FACTORY, ServerContextFactory.class.getName() );
        environment.put( Context.SECURITY_PRINCIPAL, "uid=admin,ou=system" );
        environment.put( Context.SECURITY_CREDENTIALS, password );
        environment.put( Context.SECURITY_AUTHENTICATION, "simple" );
        environment.put( Context.PROVIDER_URL, "ou=system" );
        return new InitialDirContext( environment );
    }

    public void addPartition( String name, String root, Set indexedAttributes, Attributes partitionAttributes )
        throws NamingException
    {
        MutablePartitionConfiguration configuration = new MutablePartitionConfiguration();
        configuration.setId( name );
        configuration.setSuffix( root );
        configuration.setIndexedAttributes( indexedAttributes );
        configuration.setContextEntry( partitionAttributes );
        partitionConfigurations.add( configuration );
    }

    public void addPartition( Partition partition )
        throws NamingException
    {
        MutablePartitionConfiguration configuration = new MutablePartitionConfiguration();

        configuration.setId( partition.getName() );
        configuration.setSuffix( partition.getSuffix() );
        configuration.setIndexedAttributes( partition.getIndexedAttributes() );
        configuration.setContextEntry( partition.getContextAttributes() );
        //configuration.setSynchOnWrite( true );
        configuration.setCacheSize( 1 );
        //configuration.setOptimizerEnabled( false );
        partitionConfigurations.add( configuration );
    }

    public Partition addSimplePartition( String name, String[] domainComponents )
        throws NamingException
    {
        if ( domainComponents.length == 0 )
        {
            throw new NamingException( "Illegal argument, there has to be at least one domain component." );
        }

        StringBuilder suffix = new StringBuilder();

        for ( int i = 0; i < domainComponents.length; i++ )
        {
            String dc = domainComponents[i];

            suffix.append( "dc=" ).append( dc );

            if ( i != domainComponents.length - 1 )
            {
                suffix.append( "," );
            }
        }

        // ----------------------------------------------------------------------
        // The root entry of the partition
        // ----------------------------------------------------------------------

        Attributes attributes = new BasicAttributes( true );
        attributes.put( "dc", domainComponents[0] );
        Attribute objectClass = new BasicAttribute( "objectClass" );
        objectClass.add( "top" );
        objectClass.add( "domain" );
        objectClass.add( "extensibleObject" );
        attributes.put( objectClass );

        Partition partition = new Partition();
        partition.setName( name );
        partition.setSuffix( suffix.toString() );
        partition.setContextAttributes( attributes );
        HashSet set = new HashSet();
        set.add( "uid" );
        set.add( "cn" );
        partition.setIndexedAttributes( set );

        addPartition( partition );

        return partition;
    }

    public void startServer()
        throws Exception
    {
        logger.info( "Starting Apache Directory Server server." );

        logger.info( "ApacheDS basedir: " + basedir.getAbsolutePath() );

        File logs = new File( basedir, "logs" );

        if ( !logs.exists() && !logs.mkdirs() )
        {
            throw new Exception( "Could not create logs directory: " + logs.getAbsolutePath() );
        }

        Properties environment = new Properties();
        environment.setProperty( "java.naming.security.authentication", "simple" );
        environment.setProperty( "java.naming.security.principal", "uid=admin,ou=system" );
        if ( password != null )
        {
            environment.setProperty( "java.naming.security.credentials", password );
        }
        MutableServerStartupConfiguration configuration = new MutableServerStartupConfiguration();
        configuration.setWorkingDirectory( basedir );
        configuration.setAllowAnonymousAccess( true );
        //configuration.setEnableNtp( false );
        //configuration.setEnableKerberos( false );
        //configuration.setEnableChangePassword( false );
        LdapConfiguration config = new LdapConfiguration();
        config.setIpPort( port );
        configuration.setLdapConfiguration( config );
        configuration.setEnableNetworking( enableNetworking );
        configuration.setSynchPeriodMillis( 100 );

        if ( configuration.getPartitionConfigurations() == null || ( configuration.getPartitionConfigurations() != null
            && configuration.getPartitionConfigurations().isEmpty() ) )
        {
            configuration.setPartitionConfigurations( partitionConfigurations );
        }
        Properties env = new Properties();
        env.setProperty( Context.SECURITY_PRINCIPAL, "uid=admin,ou=system" );
        if ( password != null )
        {
            env.setProperty( Context.SECURITY_CREDENTIALS, password );
        }
        env.setProperty( Context.SECURITY_AUTHENTICATION, "simple" );
        env.setProperty( Context.PROVIDER_URL, "ou=system" );
        env.setProperty( Context.INITIAL_CONTEXT_FACTORY, ServerContextFactory.class.getName() );
        env.putAll( configuration.toJndiEnvironment() );
        InitialDirContext context = new InitialDirContext( env );

        //Attributes inetAttributes = context.getAttributes( "cn=inetorgperson,ou=schema" );

        //inetAttributes.remove( "m-disabled" );

        this.configuration = configuration;

        logger.info( "Started Apache Directory Server server." );

        stopped = false;
    }

    public void stopServer()
        throws Exception
    {
        if ( stopped )
        {
            throw new Exception( "Already stopped." );
        }

        logger.info( "Stopping Apache Directory Server server." );

        sync();

        stopped = true;

        Hashtable env = new Hashtable();
        env.putAll( new ShutdownConfiguration().toJndiEnvironment() );
        new InitialDirContext( env );

        logger.info( "Apache Directory Server server stopped." );
    }

    public boolean isStopped()
    {
        return stopped;
    }

    public void sync()
        throws Exception
    {
        logger.info( "Sync'ing Apache Directory Server server." );

        Hashtable env = new Hashtable();
        env.putAll( new SyncConfiguration().toJndiEnvironment() );
        new InitialDirContext( env );
    }


    public void stop()
    {
        try
        {
            if ( !stopped )
            {
                stopServer();
            }
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Error while stopping Apache Directory Server server.", e );
        }
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    private void assertIsStarted()
        throws NamingException
    {
        if ( configuration == null )
        {
            throw new NamingException( "The server has to be started before used." );
        }
    }

    public int getPort()
    {
        return port;
    }

    public void setPort( int port )
    {
        this.port = port;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }
}

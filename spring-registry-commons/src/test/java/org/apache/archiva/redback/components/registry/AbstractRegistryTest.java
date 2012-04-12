package org.apache.archiva.redback.components.registry;

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

import junit.framework.TestCase;
import org.apache.archiva.redback.components.registry.Registry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:olamy@codehaus.org">olamy</a>
 * @version $Id$
 * @since 8 feb. 07
 */
@RunWith( value = SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath*:/META-INF/spring-context.xml", "classpath*:/spring-context.xml" } )
public abstract class AbstractRegistryTest
    extends TestCase
{

    @Inject
    protected ApplicationContext applicationContext;

    public abstract String getRoleHint();

    public Registry getRegistry()
        throws Exception
    {
        return getRegistry( getRoleHint() );
    }

    public Registry getRegistry( String name )
        throws Exception
    {
        Registry registry = applicationContext.getBean( name, Registry.class );
        registry.initialize();
        return registry;
    }

    @Test
    public void testInt()
        throws Exception
    {
        Registry registry = getRegistry();
        assertEquals( "not 2 ", 2, registry.getInt( "two" ) );
    }

    @Test
    public void testIntUnknown()
        throws Exception
    {
        Registry registry = getRegistry();
        try
        {
            registry.getInt( "unknown" );
            assertTrue( "no NoSuchElementException", false );
        }
        catch ( NoSuchElementException e )
        {
            // cool it works
        }
    }

    @Test
    public void testString()
        throws Exception
    {
        Registry registry = getRegistry();
        assertEquals( "not foo ", "foo", registry.getString( "string" ) );
    }

    @Test
    public void testStringUnknown()
        throws Exception
    {
        Registry registry = getRegistry();
        String value = registry.getString( "unknown" );
        assertNull( "unknow not null", value );

    }

    @Test
    public void testBoolean()
        throws Exception
    {
        Registry registry = getRegistry();
        assertEquals( "not true ", true, registry.getBoolean( "boolean" ) );
    }

    @Test
    public void testBooleanUnknown()
        throws Exception
    {
        Registry registry = getRegistry();
        try
        {
            registry.getBoolean( "unknown" );
            assertTrue( "no NoSuchElementException", false );
        }
        catch ( NoSuchElementException e )
        {
            // cool it works
        }
    }

    @Test
    public void testIsNotEmpty()
        throws Exception
    {
        assertFalse( getRegistry().isEmpty() );
    }

    @Test
    public void testGetSubRegistry()
        throws Exception
    {
        assertNotNull( getRegistry().getSubset( "subOne" ) );
    }

    @Test
    public void testgetSubsetValues()
        throws Exception
    {
        Registry sub = getRegistry().getSubset( "subOne" );
        assertNotNull( sub );
        assertEquals( "entryOne", sub.getString( "firstEntry" ) );
        assertEquals( "entryTwo", sub.getString( "secondEntry" ) );
    }

    @Test
    public void testgetSubsetEmpty()
        throws Exception
    {
        Registry registry = getRegistry();
        assertNotNull( registry.getSubset( "none" ) );
        assertTrue( registry.getSubset( "none" ).isEmpty() );

    }

    @Test
    public void testSetBoolean()
        throws Exception
    {
        Registry registry = getRegistry();
        registry.setBoolean( "keyTrue", true );
        assertTrue( registry.getBoolean( "keyTrue" ) );
    }

    @Test
    public void testSetInt()
        throws Exception
    {
        Registry registry = getRegistry();
        registry.setInt( "keyInt", 3 );
        assertEquals( 3, registry.getInt( "keyInt" ) );
    }

    @Test
    public void testSetString()
        throws Exception
    {
        Registry registry = getRegistry();
        registry.setString( "what", "zorglub" );
        assertEquals( "zorglub", registry.getString( "what" ) );
    }
}

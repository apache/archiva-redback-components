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

import org.jpox.PersistenceManagerFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Test for {@link org.apache.archiva.redback.components.jdo.DefaultConfigurableJdoFactory}
 *
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(
    locations = { "classpath*:/META-INF/spring-context.xml", "classpath*:/spring-context-configurable.xml" } )
public class DefaultConfigurableJdoFactoryTest
    extends DefaultJdoFactoryTest
{

    @Inject
    @Named( value = "jdoFactory" )
    DefaultConfigurableJdoFactory jdoFactory;

    @Test
    public void testLoad()
        throws Exception
    {
        String password = jdoFactory.getProperties().getProperty( "javax.jdo.option.ConnectionPassword" );
        assertNull( password );

        PersistenceManagerFactoryImpl pmf = (PersistenceManagerFactoryImpl) jdoFactory.getPersistenceManagerFactory();
        assertTrue( pmf.getAutoCreateTables() );
    }

}

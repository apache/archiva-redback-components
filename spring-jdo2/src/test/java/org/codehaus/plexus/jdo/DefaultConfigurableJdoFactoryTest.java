package org.codehaus.plexus.jdo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jdo.PersistenceManager;

import org.jpox.PersistenceManagerFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * Copyright 2006 The Apache Software Foundation.
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

/**
 * Test for {@link DefaultConfigurableJdoFactory}
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath*:/META-INF/spring-context.xml", "classpath*:/spring-context-configurable.xml" } )
public class DefaultConfigurableJdoFactoryTest
    extends DefaultJdoFactoryTest
{

    @Inject @Named(value = "jdoFactory")
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

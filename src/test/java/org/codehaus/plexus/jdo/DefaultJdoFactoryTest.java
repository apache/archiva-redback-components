package org.codehaus.plexus.jdo;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author David Wynter
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath*:/META-INF/spring-context.xml", "classpath*:/spring-context.xml" } )
public class DefaultJdoFactoryTest
    extends TestCase
{

    @Inject
    JdoFactory jdoFactory;

    @Test
    public void testBasic()
        throws Exception
    {

        PersistenceManagerFactory pmf = jdoFactory.getPersistenceManagerFactory();

        PersistenceManager pm = pmf.getPersistenceManager();
/*
        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            Parent parent = new Parent( "Sony Discman", "A standard discman from Sony", 49.99 );

            pm.makePersistent( parent );

            tx.commit();
        }
        finally
        {
            if ( tx.isActive() )
            {
                tx.rollback();
            }

            pm.close();
        }
*/
    }
}

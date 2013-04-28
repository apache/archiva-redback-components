package org.apache.archiva.redback.components.springutils.plexusshim;

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

import junit.framework.Assert;
import org.apache.maven.index.NexusIndexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * @author Olivier Lamy
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(
    locations = { "classpath*:/META-INF/spring-context.xml" } )
public class PlexusShimComponentTest
{

    @Inject
    private PlexusShimComponent plexusShimComponent;

    @Test
    public void testMavenIndexerLoad()
        throws Exception
    {
        NexusIndexer nexusIndexer = plexusShimComponent.lookup( NexusIndexer.class );
        Assert.assertNotNull( nexusIndexer );
    }
}

package org.codehaus.modello.plugin.registry;

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

import org.apache.archiva.redback.components.registry.commons.CommonsConfigurationRegistry;
import org.codehaus.modello.test.model.Model;
import org.codehaus.modello.test.model.Reference;
import org.codehaus.modello.test.model.io.registry.ModelRegistryReader;
import org.codehaus.modello.verifier.Verifier;
import org.apache.archiva.redback.components.registry.Registry;

import junit.framework.Assert;

import java.io.File;
import java.lang.AssertionError;
import java.lang.System;
import java.util.*;


/**
 * @author <a href="mailto:brett@apache.org">Brett Porter</a>
 *
 */
public class RegistryReaderVerifier
    extends Verifier
{
    public void verify()
        throws Exception
    {
        Registry registry = new CommonsConfigurationRegistry();
        registry.initialize();
        registry.addConfigurationFromFile( new File( "src/test/verifiers/registry-reader/test.properties" ) );
        registry.addConfigurationFromFile( new File( "src/test/verifiers/registry-reader/test.xml" ) );

        ModelRegistryReader modelReader = new ModelRegistryReader();

        Model model = modelReader.read( registry );

        Assert.assertEquals( "Name", model.getName() );
        Assert.assertEquals( System.getProperty( "user.home" ) + "/.m2/repository", model.getRepository() );
        Assert.assertEquals( 1, model.getNumeric() );
        Assert.assertEquals( "RefName", model.getReference().getName() );
        Assert.assertNull( model.getMissingReference().getName() );
        Assert.assertNotNull( model.getEmptyReference() );
        Assert.assertEquals( "ListName1", ((Reference)model.getListReferences().get( 0 )).getName() );
        Assert.assertEquals( "ListName2", ((Reference)model.getListReferences().get( 1 )).getName() );
        Assert.assertEquals( "ListName3", ((Reference)model.getListReferences().get( 2 )).getName() );
        Set set = model.getSetReferences();
        List names = new ArrayList( set.size() );
        for ( Iterator i = set.iterator(); i.hasNext(); )
        {
            Reference ref = (Reference) i.next();
            names.add( ((Reference)ref).getName() );
        }
        Collections.sort( names );
        Assert.assertEquals( Arrays.asList( new String[] { "SetName1", "SetName2" } ), names );
        Assert.assertEquals( Arrays.asList( new String[] { "S1", "S2", "S3", "S4", "S5" } ), model.getStringReferences() );

        Map map = model.getMap();
        Assert.assertEquals( 3, map.size() );
        Assert.assertEquals( "value1", map.get( "property" ) );
        Assert.assertEquals( "value2", map.get( "property2" ) );
        Assert.assertEquals( "value3", map.get( "something.else" ) );

        Properties properties = model.getProperties();
        Assert.assertEquals( 3, properties.size() );
        Assert.assertEquals( "value1", properties.getProperty( "property" ) );
        Assert.assertEquals( "value2", properties.getProperty( "property2" ) );
        Assert.assertEquals( "value3", properties.getProperty( "something.else" ) );

        // test defaults
        Assert.assertEquals( "def", model.getDefString() );
        Assert.assertEquals( 8080, model.getDefNumeric() );
        Assert.assertEquals( true, model.isDefBoolean() );

        System.out.println("baseDn:" + model.getBaseDn());

        Assert.assertEquals( "ou=People,dc=archiva,dc=apache,dc=org", model.getBaseDn() );
    }
}

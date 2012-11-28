package org.apache.archiva.redback.components.modello.plugin.store;

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

import org.codehaus.modello.AbstractModelloGeneratorTest;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.core.ModelloCore;
import org.codehaus.modello.model.Model;
import org.codehaus.plexus.util.ReaderFactory;

import java.util.Properties;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: StoreModelloGeneratorTest.java 840 2007-07-17 18:50:39Z hboutemy $
 */
public class StoreModelloGeneratorTest
    extends AbstractModelloGeneratorTest
{
    public StoreModelloGeneratorTest()
    {
        super( "store" );
    }

    public void testSimpleInvocation()
        throws Exception
    {
        ModelloCore core = (ModelloCore) lookup( ModelloCore.ROLE );

        Model model = core.loadModel( ReaderFactory.newXmlReader( getTestFile( "src/test/resources/mergere-tissue.mdo" ) ) );

        Properties parameters = new Properties();

        parameters.setProperty( ModelloParameterConstants.OUTPUT_DIRECTORY, getOutputDirectory().getPath() );

        parameters.setProperty( ModelloParameterConstants.VERSION, "4.0.0" );

        parameters.setProperty( ModelloParameterConstants.PACKAGE_WITH_VERSION, Boolean.toString( false ) );

        core.generate( model, "store", parameters );
    }
}

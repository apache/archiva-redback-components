package org.apache.archiva.redback.components.modello.jpox;

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

import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.Version;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.ReaderFactory;

import java.util.List;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: StaxGeneratorTest.java 675 2006-11-16 10:58:59Z brett $
 */
public class JpoxDeleteModelTest
    extends AbstractJpoxGeneratorTestCase
{
    public JpoxDeleteModelTest()
        throws ComponentLookupException
    {
        super( "jpox-delete-model" );
    }

    public void testJpoxReaderVersionInField()
        throws Throwable
    {
        if ( true )
        {
            return;
        }

        Model model = modello.loadModel( ReaderFactory.newXmlReader( getTestFile( "src/test/resources/test.mdo" ) ) );

        List classesList = model.getClasses( new Version( "1.0.0" ) );

        assertEquals( 5, classesList.size() );

        ModelClass clazz = (ModelClass) classesList.get( 0 );

        assertEquals( "JdoRole", clazz.getName() );

        verifyModel( model, "org.codehaus.modello.plugin.jpox.JpoxVerifierDeleteModel" );
    }

}

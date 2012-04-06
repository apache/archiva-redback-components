package org.codehaus.redback.components.scheduler;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
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

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {"classpath*:/META-INF/spring-context.xml","classpath:/spring-context.xml"} )
public class CronExpressionValidatorTest
    extends TestCase
{

    @Inject
    CronExpressionValidator validator;

    @Test
    public void testValidation()
        throws Exception
    {

        assertTrue( validator.validate( "0 0 * * * ?" ) );

        assertTrue( validator.validate( "0 0 * ? * *" ) );

        assertFalse( validator.validate( "0 0 4-1 * * ?" ) );

        assertTrue( validator.validate( "0 0 1-4 * * ?" ) );

        assertTrue( validator.validate( "0 0,15,30,45 * * * ?" ) );

        assertFalse( validator.validate( "0 0,45,15,30 * * * ?" ) );

        assertTrue( validator.validate( "0 0 12 * * ?" ) );

        assertTrue( validator.validate( "0 15 10 ? * *" ) );

        assertTrue( validator.validate( "0 15 10 * * ?" ) );

        assertTrue( validator.validate( "0 15 10 * * ? *" ) );

        assertTrue( validator.validate( "0 15 10 * * ? 2005" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 2100" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 1969" ) );

        assertTrue( validator.validate( "0 15 10 * * ? 2005-2007" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 2005-2100" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 1960-2010" ) );

        assertTrue( validator.validate( "0 15 10 * * ? 2005/2" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 2100/3" ) );

        assertFalse( validator.validate( "0 15 10 * * ? 1960/10" ) );

        assertTrue( validator.validate( "0 * 14 * * ?" ) );

        assertTrue( validator.validate( "0 0/5 14 * * ?" ) );

        assertTrue( validator.validate( "0 0/5 14,18 * * ?" ) );

        assertTrue( validator.validate( "0 0-5 14 * * ?" ) );

        assertTrue( validator.validate( "0 10,44 14 ? 3 WED" ) );

        assertTrue( validator.validate( "0 15 10 ? * MON-FRI" ) );

        assertTrue( validator.validate( "0 15 10 15 * ?" ) );

        assertTrue( validator.validate( "0 15 10 L * ?" ) );

        assertFalse( validator.validate( "0 15 10 6#3 * ?" ) );

        assertTrue( validator.validate( "0 15 10 15W * ?" ) );

        assertFalse( validator.validate( "0 15 10 15W1 * ?" ) );

        assertTrue( validator.validate( "0 15 10 ? * 6L" ) );

        assertTrue( validator.validate( "0 15 10 ? * 6L" ) );

        assertTrue( validator.validate( "0 15 10 ? * 6L 2002-2005" ) );

        assertFalse( validator.validate( "0 15 10 ? * 6L3 2002-2005" ) );

        assertTrue( validator.validate( "0 15 10 ? * 6#3" ) );

        assertFalse( validator.validate( "0 15 10 ? * 6#" ) );

        assertFalse( validator.validate( "0 15 10 ? * #3" ) );

        assertFalse( validator.validate( "0 15 10 ? * 8#3" ) );

        assertFalse( validator.validate( "0 15 10 ? * 6#6" ) );

        assertFalse( validator.validate( "0 0" ) );

        assertFalse( validator.validate( "0 0 * * * *" ) );

        assertFalse( validator.validate( "a a a a a a" ) );

        assertFalse( validator.validate( "0 0 0 ? 0 A" ) );
    }
}

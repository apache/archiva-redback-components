package org.codehaus.modello.plugin.store.tool;

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

import org.codehaus.modello.model.ModelField;
import org.codehaus.plexus.util.StringUtils;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: JavaTool.java 803 2007-02-09 11:12:55Z brett $
 */
public class JavaTool
{
    public String makeGetter( ModelField field )
    {
        if ( field.getType().equals( "boolean" ) )
        {
            return "is" + field.getName().substring( 0, 1 ).toUpperCase() + field.getName().substring( 1 );
        }

        return "get" + field.getName().substring( 0, 1 ).toUpperCase() + field.getName().substring( 1 );
    }

    public String makeSetter( ModelField field )
    {
        return "set" + field.getName().substring( 0, 1 ).toUpperCase() + field.getName().substring( 1 );
    }

    public void fail( String message )
        throws Exception
    {
        throw new Exception( message );
    }

    public String uncapitalise( String s )
    {
        return StringUtils.uncapitalise( s );
    }

    public String capitalise( String s )
    {
        return StringUtils.capitalise( s );
    }

    public String singular( String name )
    {
        if ( name.endsWith( "ies" ) )
        {
            return name.substring( 0, name.length() - 3 ) + "y";
        }
        else if ( name.endsWith( "es" ) && name.endsWith( "ches" ) )
        {
            return name.substring( 0, name.length() - 2 );
        }
        else if ( name.endsWith( "s" ) )
        {
            return name.substring( 0, name.length() - 1 );
        }

        return name;
    }
}

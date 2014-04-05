package org.apache.archiva.redback.components.evaluator;

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

import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DefaultExpressionEvaluator 
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 *
 */
public class DefaultExpressionEvaluator
    implements ExpressionEvaluator
{
    private List<ExpressionSource> expressionSources;

    public DefaultExpressionEvaluator()
    {
        expressionSources = new ArrayList<>();
    }

    public void addExpressionSource( ExpressionSource source )
    {
        expressionSources.add( source );
    }

    public String expand( String str )
        throws EvaluatorException
    {
        return recursiveExpand( str, new ArrayList<String>() );
    }

    private String recursiveExpand( String str, List<String> seenExpressions )
        throws EvaluatorException
    {
        if ( StringUtils.isEmpty( str ) )
        {
            // Empty string. Fail fast.
            return str;
        }

        if ( str.indexOf( "${" ) < 0 )
        {
            // Contains no potential expressions.  Fail fast.
            return str;
        }

        if ( this.expressionSources.isEmpty() )
        {
            throw new EvaluatorException( "Unable to expand expressions with empty ExpressionSource list." );
        }

        Pattern pat = Pattern.compile( "(?<=[^$]|^)(\\$\\{[^}]*\\})" );
        Matcher mat = pat.matcher( str );
        int offset = 0;
        String expression;
        String value;
        StringBuilder expanded = new StringBuilder();

        while ( mat.find( offset ) )
        {
            expression = mat.group( 1 );

            if ( seenExpressions.contains( expression ) )
            {
                throw new EvaluatorException( "A recursive cycle has been detected with expression " + expression + "." );
            }

            seenExpressions.add( expression );

            expanded.append( str.substring( offset, mat.start( 1 ) ) );
            value = findValue( expression );
            if ( value != null )
            {
                String resolvedValue = recursiveExpand( value, seenExpressions );
                expanded.append( resolvedValue );
            }
            else
            {
                expanded.append( expression );
            }
            offset = mat.end( 1 );
        }

        expanded.append( str.substring( offset ) );

        if ( expanded.indexOf( "$$" ) >= 0 )
        {
            // Special case for escaped content.
            return expanded.toString().replaceAll( "\\$\\$", "\\$" );
        }
        else
        {
            // return expanded
            return expanded.toString();
        }
    }

    private String findValue( String expression )
    {
        String newExpression = expression.trim();
        if ( newExpression.startsWith( "${" ) && newExpression.endsWith( "}" ) )
        {
            newExpression = newExpression.substring( 2, newExpression.length() - 1 );
        }

        if ( StringUtils.isEmpty( newExpression ) )
        {
            return null;
        }

        String value = null;
        Iterator it = this.expressionSources.iterator();
        while ( it.hasNext() )
        {
            ExpressionSource source = (ExpressionSource) it.next();
            value = source.getExpressionValue( newExpression );
            if ( value != null )
            {
                return value;
            }
        }
        return null;
    }

    public List getExpressionSourceList()
    {
        return this.expressionSources;
    }

    public boolean removeExpressionSource( ExpressionSource source )
    {
        return this.expressionSources.remove( source );
    }
}

package org.codehaus.redback.components.springutils;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * see http://jawspeak.com/2010/11/28/spring-slow-autowiring-by-type-getbeannamesfortype-fix-10x-speed-boost-3600ms-to/
 *
 * @author Olivier Lamy
 */
public class CachingByTypeBeanFactory
    extends DefaultListableBeanFactory
{

    private Logger log = LoggerFactory.getLogger( CachingByTypeBeanFactory.class );

    ConcurrentHashMap<TypeKey, String[]> cachedBeanNamesForType = new ConcurrentHashMap<TypeKey, String[]>();

    /**
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForType(java.lang.Class)
     */
    @Override
    public String[] getBeanNamesForType( @SuppressWarnings( "rawtypes" ) Class type )
    {
        return getBeanNamesForType( type, true, true );
    }

    @Override
    public String[] getBeanNamesForType( @SuppressWarnings( "rawtypes" ) Class type, boolean includeNonSingletons,
                                         boolean allowEagerInit )
    {
        TypeKey typeKey = new TypeKey( type, includeNonSingletons, allowEagerInit );
        if ( cachedBeanNamesForType.containsKey( typeKey ) )
        {
            log.debug( "will retrieve from cache: {}", typeKey );
            return cachedBeanNamesForType.get( typeKey );
        }
        String[] value = super.getBeanNamesForType( type, includeNonSingletons, allowEagerInit );
        if ( log.isDebugEnabled() )
        {
            log.debug( "will add to cache: {} : {}", typeKey, Arrays.asList( value ) );
        }
        cachedBeanNamesForType.putIfAbsent( typeKey, value );
        return value;
    }

    // This is the input parameters, which we memoize.
    // We conservatively cache based on the possible parameters passed in. Assuming that state does not change within the
    // super.getBeanamesForType() call between subsequent requests.
    static class TypeKey
    {
        Class<?> type;

        boolean includeNonSingletons;

        boolean allowEagerInit;

        TypeKey( Class<?> type, boolean includeNonSingletons, boolean allowEagerInit )
        {
            this.type = type;
            this.includeNonSingletons = includeNonSingletons;
            this.allowEagerInit = allowEagerInit;
        }

        @Override
        public String toString()
        {
            return "TypeKey{" + type + " " + includeNonSingletons + " " + allowEagerInit + "}";
        }

        @Override
        public boolean equals( Object o )
        {
            if ( this == o )
            {
                return true;
            }
            if ( o == null || getClass() != o.getClass() )
            {
                return false;
            }

            TypeKey typeKey = (TypeKey) o;

            if ( allowEagerInit != typeKey.allowEagerInit )
            {
                return false;
            }
            if ( includeNonSingletons != typeKey.includeNonSingletons )
            {
                return false;
            }
            if ( type != null ? !type.equals( typeKey.type ) : typeKey.type != null )
            {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode()
        {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + ( includeNonSingletons ? 1 : 0 );
            result = 31 * result + ( allowEagerInit ? 1 : 0 );
            return result;
        }
    }

}

package org.codehaus.plexus.cache.builder;

/*
 * Copyright 2001-2007 The Codehaus.
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

import org.codehaus.plexus.cache.Cache;
import org.codehaus.plexus.cache.impl.NoCacheCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * Ability to obtain cache
 *
 * @author Olivier Lamy
 * @version $Id$
 * @plexus.component role="org.codehaus.plexus.cache.builder.CacheBuilder" role-hint="default"
 */
@Service
public class DefaultCacheBuilder
    implements CacheBuilder
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    private Cache defaultCache;

    private Cache noCache = new NoCacheCache();

    @Inject
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initialize()
    {
        if ( this.applicationContext.containsBean( "cache#default" ) )
        {
            this.defaultCache = this.applicationContext.getBean( "cache#default", Cache.class );

        }
        else
        {
            log.info( "Cache with role-hint default doesn't exists, default will be no cache" );
            this.defaultCache = new NoCacheCache();
        }
    }

    public Cache getCache( String roleHint )
    {
        if ( this.applicationContext.containsBean( "cache#" + roleHint ) )
        {
            return this.applicationContext.getBean( "cache#" + roleHint, Cache.class );
        }

        return this.getDefaultCache();
    }

    public Cache getCache( Class clazz )
    {
        return this.getCache( clazz.getName() );
    }

    @PreDestroy
    public void dispose()
    {
        // TODO dispose default ?
    }

    public Cache getDefaultCache()
    {
        return defaultCache == null ? this.noCache : this.defaultCache;
    }
}

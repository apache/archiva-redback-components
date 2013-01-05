package org.apache.archiva.redback.components.cache.ehcache;

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

import org.apache.archiva.redback.components.cache.Cache;
import org.apache.archiva.redback.components.cache.ehcache.EhcacheCache;
import org.apache.archiva.redback.components.cache.test.AbstractCacheTestCase;
import org.apache.archiva.redback.components.cache.test.examples.wine.Wine;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * EhcacheCacheTest 
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 *
 */
public class EhcacheCacheTest
    extends AbstractCacheTestCase
{

    @Inject @Named(value = "cache#ehcache")
    Cache<String,Integer> cache;

    @Inject @Named(value = "cache#alwaysrefresh")
    Cache<String,Wine> cachealwaysrefresh;

    @Inject @Named(value = "cache#onesecondrefresh")
    Cache<String,Wine> cacheonesecondrefresh;

    @Inject @Named(value = "cache#onesecondrefresh")
    Cache<String,Wine> cachetwosecondrefresh;

    @Inject @Named(value = "cache#neverrefresh")
    Cache<String,Wine> cacheneversecondrefresh;

    @Override
    public Cache<String,Integer> getCache()
    {
        return cache;
    }

    public Cache<String,Wine> getAlwaysRefresCache()
        throws Exception
    {
        return cachealwaysrefresh;
    }

    public Cache<String,Wine> getNeverRefresCache()
        throws Exception
    {
        return cacheneversecondrefresh;
    }

    public Cache<String,Wine> getOneSecondRefresCache()
        throws Exception
    {
        return cacheonesecondrefresh;
    }

    public Cache<String,Wine> getTwoSecondRefresCache()
        throws Exception
    {
        return cachetwosecondrefresh;
    }

    public Class getCacheClass()
    {
        return EhcacheCache.class;
    }
}

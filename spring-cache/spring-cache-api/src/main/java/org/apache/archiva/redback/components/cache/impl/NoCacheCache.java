package org.apache.archiva.redback.components.cache.impl;

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

import org.apache.archiva.redback.components.cache.AbstractCache;
import org.apache.archiva.redback.components.cache.CacheStatistics;
import org.apache.archiva.redback.components.cache.Cache;


/**
 * Simple no-op provider of a Cache.
 * <p/>
 * Nothing is stored, nothing is tracked, no statistics even.
 *
 * @author Olivier Lamy
 * @since 5 February, 2007
 */
public class NoCacheCache
    extends AbstractCache
    implements Cache
{
    class NoStats
        implements CacheStatistics
    {

        public void clear()
        {
            /* do nothing */
        }

        public double getCacheHitRate()
        {
            return 0;
        }

        public long getCacheHits()
        {
            return 0;
        }

        public long getCacheMiss()
        {
            return 0;
        }

        public long getSize()
        {
            return 0;
        }

        public long getInMemorySize()
        {
            return 0;
        }
    }

    private CacheStatistics stats = new NoStats();

    public void clear()
    {
        /* do nothing */
    }

    public Object get( Object key )
    {
        return null;
    }

    public CacheStatistics getStatistics()
    {
        return stats;
    }

    public boolean hasKey( Object key )
    {
        return false;
    }

    public Object put( Object key, Object value )
    {
        return null;
    }

    public void register( Object key, Object value )
    {
        /* do nothing */
    }

    public Object remove( Object key )
    {
        return null;
    }

    public <T> T get( Object key, Class<T> clazz )
    {
        return null;
    }

    public <T> T put( Object key, Object value, Class<T> clazz )
    {
        return null;
    }
}

package org.apache.archiva.redback.components.cache.hashmap;

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
import org.apache.archiva.redback.components.cache.AbstractCacheStatistics;
import org.apache.archiva.redback.components.cache.Cache;
import org.apache.archiva.redback.components.cache.CacheStatistics;
import org.apache.archiva.redback.components.cache.CacheableWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * HashMapCache - this is a Cache implementation taken from the Archiva project.
 * </p>
 * <p/>
 * <p>
 * Original class written by Edwin Punzalan for purposes of addressing the
 * jira ticket <a href="http://jira.codehaus.org/browse/MRM-39">MRM-39</a>
 * </p>
 * <p>
 * Configure the refreshTime in seconds value configure a ttl of object life in cache.
 * Object get( Object key ) :
 * <ul>
 * <li> &lt; 0 : method will always return null (no cache)</li>
 * <li> = 0 : first stored object will be return (infinite life in the cache)</li>
 * <li> > 0 : after a live (stored time) of refreshTime the object will be remove from the cache
 * and a no object will be returned by the method</li>
 * </ul>
 * </p>
 *
 * @author Edwin Punzalan
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 */
@Service("cache#hashmap")
public class HashMapCache<V,T>
    extends AbstractCache<V,T>
    implements Cache<V,T>
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    class Stats
        extends AbstractCacheStatistics
        implements CacheStatistics
    {

        public Stats()
        {
            super();
        }

        public long getSize()
        {
            synchronized ( cache )
            {
                return cache.size();
            }
        }

    }

    private Map<V,CacheableWrapper<T>> cache;

    /**
     *
     */
    private double cacheHitRatio = 1.0;

    /**
     *
     */
    private int cacheMaxSize = 0;

    /**
     *
     *
     */
    private int refreshTime;

    private Stats stats;

    public HashMapCache()
    {
        // noop
    }

    /**
     * Empty the cache and reset the cache hit rate
     */
    public void clear()
    {
        synchronized ( cache )
        {
            stats.clear();
            cache.clear();
        }
    }

    /**
     * Check for a cached object and return it if it exists. Returns null when the keyed object is not found
     *
     * @param key the key used to map the cached object
     * @return the object mapped to the given key, or null if no cache object is mapped to the given key
     */
    public T get( V key )
    {
        CacheableWrapper<T> retValue = null;
        // prevent search
        if ( !this.isCacheAvailable() )
        {
            return null;
        }
        synchronized ( cache )
        {
            if ( cache.containsKey( key ) )
            {
                // remove and put: this promotes it to the top since we use a linked hash map
                retValue = cache.remove( key );

                if ( needRefresh( retValue ) )
                {
                    stats.miss();
                    return null;
                }
                else
                {
                    cache.put( key, retValue );
                    stats.hit();
                }
            }
            else
            {
                stats.miss();
            }
        }

        return retValue == null ? null : retValue.getValue();
    }



    protected boolean needRefresh( CacheableWrapper cacheableWrapper )
    {
        if ( cacheableWrapper == null )
        {
            return true;
        }
        if ( this.getRefreshTime() == 0 )
        {
            return false;
        }
        boolean result =
            ( System.currentTimeMillis() - cacheableWrapper.getStoredTime() ) > ( this.getRefreshTime() * 1000 );

        log.debug( "{} is uptodate {}", cacheableWrapper, result );

        return result;
    }

    public CacheStatistics getStatistics()
    {
        return stats;
    }

    /**
     * Check if the specified key is already mapped to an object.
     *
     * @param key the key used to map the cached object
     * @return true if the cache contains an object associated with the given key
     */
    public boolean hasKey( V key )
    {
        // prevent search
        if ( !this.isCacheAvailable() )
        {
            return false;
        }
        boolean contains;
        synchronized ( cache )
        {
            contains = cache.containsKey( key );

            if ( contains )
            {
                stats.hit();
            }
            else
            {
                stats.miss();
            }
        }

        return contains;
    }

    @PostConstruct
    public void initialize()
    {
        stats = new Stats();

        if ( cacheMaxSize > 0 )
        {
            cache = new LinkedHashMap<V,CacheableWrapper<T>>( cacheMaxSize );
        }
        else
        {
            cache = new LinkedHashMap<V,CacheableWrapper<T>>();
        }
    }

    /**
     * Cache the given value and map it using the given key
     *
     * @param key   the object to map the valued object
     * @param value the object to cache
     */
    public T put( V key, T value )
    {
        CacheableWrapper<T> ret = null;

        // remove and put: this promotes it to the top since we use a linked hash map
        synchronized ( cache )
        {
            if ( cache.containsKey( key ) )
            {
                cache.remove( key );
            }

            ret = cache.put( key, new CacheableWrapper<T>( value, System.currentTimeMillis() ) );
        }

        manageCache();

        return ret == null ? null : ret.getValue();
    }

    /**
     * Cache the given value and map it using the given key
     *
     * @param key   the object to map the valued object
     * @param value the object to cache
     */
    public void register( V key, T value )
    {
        // remove and put: this promotes it to the top since we use a linked hash map
        synchronized ( cache )
        {
            if ( cache.containsKey( key ) )
            {
                cache.remove( key );
            }

            cache.put( key, new CacheableWrapper<T>( value, System.currentTimeMillis() ) );
        }

        manageCache();
    }

    public T remove( V key )
    {
        synchronized ( cache )
        {
            if ( cache.containsKey( key ) )
            {
                return cache.remove( key ).getValue();
            }
        }

        return null;
    }

    private void manageCache()
    {
        synchronized ( cache )
        {
            Iterator iterator = cache.entrySet().iterator();
            if ( cacheMaxSize == 0 )
            {
                // desired HitRatio is reached, we can trim the cache to conserve memory
                if ( cacheHitRatio <= stats.getCacheHitRate() )
                {
                    iterator.next();
                    iterator.remove();
                }
            }
            else if ( cache.size() > cacheMaxSize )
            {
                // maximum cache size is reached
                while ( cache.size() > cacheMaxSize )
                {
                    iterator.next();
                    iterator.remove();
                }
            }
            else
            {
                // even though the max has not been reached, the desired HitRatio is already reached,
                // so we can trim the cache to conserve memory
                if ( cacheHitRatio <= stats.getCacheHitRate() )
                {
                    iterator.next();
                    iterator.remove();
                }
            }
        }
    }


    public int getRefreshTime()
    {
        return refreshTime;
    }

    /**
     *
     */
    public void setRefreshTime( int refreshTime )
    {
        this.refreshTime = refreshTime;
    }

    /**
     * @return
     */
    protected boolean isCacheAvailable()
    {
        return this.getRefreshTime() >= 0;
    }

    public double getCacheHitRatio()
    {
        return cacheHitRatio;
    }

    public void setCacheHitRatio( double cacheHitRatio )
    {
        this.cacheHitRatio = cacheHitRatio;
    }

    public int getCacheMaxSize()
    {
        return cacheMaxSize;
    }

    public void setCacheMaxSize( int cacheMaxSize )
    {
        this.cacheMaxSize = cacheMaxSize;
    }

    public Stats getStats()
    {
        return stats;
    }
}

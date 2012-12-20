package org.apache.archiva.redback.components.cache;

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

/**
 * Cache interface.
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 */
public interface Cache
{
    /**
     * Tests to see if the provided key exists within the cache.
     * <p/>
     * NOTE: Due to synchronization issues, if this method returns true, a subsequent request
     * to the {@link #get(Object)} method on the same key might return null as the period of time
     * between the 2 request might have been long enough for the underlying Cache implementation
     * to remove the key.
     *
     * @param key the key to test.
     * @return true if the key exists.
     */
    boolean hasKey( Object key );

    /**
     * Get the value of the specified key, if it exists in the cache.
     *
     * @param key the key to fetch the contents of.
     * @return the value of the key, or null if not found.
     */
    Object get( Object key );

    /**
     * Get the value of the specified key, if it exists in the cache.
     *
     * @param key   the key to fetch the contents of.
     * @param clazz class of object type to retrieve
     * @since 2.1
     * @return the value of the key, or null if not found.
     */
    <T> T get( Object key, Class<T> clazz );

    /**
     * Put the specified value into the cache under the provided key.
     *
     * @param key   the key to put the value into
     * @param value the object to place into the cache.
     * @return the previous value for the key, or null if the key contained no value.
     */
    Object put( Object key, Object value );

    /**
     * Put the specified value into the cache under the provided key.
     *
     * @param key   the key to put the value into
     * @param value the object to place into the cache.
     * @param clazz class of object type to retrieve
     * @return the previous value for the key, or null if the key contained no value.
     */
    <T> T put( Object key, Object value, Class<T> clazz );

    /**
     * Register the specified value into the cache under the provided key.
     * <p/>
     * This {@link #register(Object, Object)} method is just an optimized version of the {@link #put(Object, Object)}
     * method, but does not return the previous value contained with the specified key.
     *
     * @param key   the key to put the value into
     * @param value the object to place into the cache.
     */
    void register( Object key, Object value );

    /**
     * Remove the specified key and value from the cache.
     *
     * @param key the key to the value to remove.
     * @return the value of the key that was removed.
     */
    Object remove( Object key );

    /**
     * Clear the cache of all entries.
     */
    void clear();

    /**
     * Obtain a set of Statistics about the performance of the cache.
     *
     * @return the cache statistics.
     */
    CacheStatistics getStatistics();

    void setTimeToIdleSeconds( int timeToIdleSeconds );

    void setTimeToLiveSeconds( int timeToLiveSeconds );

    int getTimeToIdleSeconds();

    int getTimeToLiveSeconds();

}

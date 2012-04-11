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
 * CacheStatistics
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 * @version $Id$
 */
public interface CacheStatistics
{
    /**
     * Return the number of hits to content present in the cache.
     *
     * @return the number of hits to content present in the cache.
     */
    long getCacheHits();

    /**
     * Return the number of hits to keys that are not (yet) in the cache.
     *
     * @return the number of requests for content missing from the cache.
     */
    long getCacheMiss();

    /**
     * Compute for the efficiency of this cache.
     *
     * @return the ratio of cache hits to the cache misses to queries for cache objects
     */
    double getCacheHitRate();

    /**
     * Return the size of the current cache.
     *
     * @return the size of the current cache.
     */
    long getSize();

    /**
     * Clear the statistics of the cache.
     */
    void clear();
}

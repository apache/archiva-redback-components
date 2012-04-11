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
 * @version $Id$
 * @author Olivier Lamy
 */
public abstract class AbstractCacheStatistics
    implements CacheStatistics
{

    private long cacheHits;

    private long cacheMiss;

    public AbstractCacheStatistics()
    {
        this.cacheHits = 0;
        this.cacheMiss = 0;
    }

    public long getCacheHits()
    {
        return this.cacheHits;
    }

    public long getCacheMiss()
    {
        return this.cacheMiss;
    }

    public double getCacheHitRate()
    {
        return cacheHits == 0 && cacheMiss == 0 ? 0 : (double) cacheHits / (double) ( cacheHits + cacheMiss );
    }

    public abstract long getSize();

    public void hit()
    {
        this.cacheHits++;
    }

    public void miss()
    {
        this.cacheMiss++;
    }

    public void clear()
    {
        this.cacheHits = 0;
        this.cacheMiss = 0;
    }

}

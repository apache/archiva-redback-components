package org.apache.archiva.redback.components.taskqueue;

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


import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public interface TaskQueue
{

    // ----------------------------------------------------------------------
    // Queue operations
    // ----------------------------------------------------------------------

    /**
     * @param task The task to add to the queue.
     * @return Returns true if the task was accepted into the queue.
     */
    boolean put( Task task )
        throws TaskQueueException;

    Task take()
        throws TaskQueueException;

    boolean remove( Task task )
        throws ClassCastException, NullPointerException;

    boolean removeAll( List tasks )
        throws ClassCastException, NullPointerException;

    // ----------------------------------------------------------------------
    // Queue Inspection
    // ----------------------------------------------------------------------

    List getQueueSnapshot()
        throws TaskQueueException;

    /**
     * Retrieves and removes the head of the queue, waiting at most timeout timeUnit when no element is available.
     *
     * @param timeout  time to wait, in timeUnit units
     * @param timeUnit how to interpret the timeout parameter.
     * @return the head of the queue, or null if the timeout elapsed
     * @throws InterruptedException when this thread is interrupted while waiting
     */
    Task poll( int timeout, TimeUnit timeUnit )
        throws InterruptedException;
}

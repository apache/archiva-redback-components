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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 *
 */
public class DefaultTaskQueue
    implements TaskQueue
{

    private Logger logger = LoggerFactory.getLogger( getClass() );

    private List<TaskEntryEvaluator> taskEntryEvaluators = new ArrayList<TaskEntryEvaluator>();

    private List<TaskExitEvaluator> taskExitEvaluators = new ArrayList<TaskExitEvaluator>();

    private List<TaskViabilityEvaluator> taskViabilityEvaluators = new ArrayList<TaskViabilityEvaluator>();

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();

    // ----------------------------------------------------------------------
    // Component Lifecycle
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // TaskQueue Implementation
    // ----------------------------------------------------------------------

    // ----------------------------------------------------------------------
    // Queue operations
    // ----------------------------------------------------------------------

    public boolean put( Task task )
        throws TaskQueueException
    {
        // ----------------------------------------------------------------------
        // Check that all the task entry evaluators accepts the task
        // ----------------------------------------------------------------------

        for ( TaskEntryEvaluator taskEntryEvaluator : taskEntryEvaluators )
        {
            boolean result = taskEntryEvaluator.evaluate( task );

            if ( !result )
            {
                return false;
            }
        }

        // ----------------------------------------------------------------------
        // The task was accepted, enqueue it
        // ----------------------------------------------------------------------

        enqueue( task );

        // ----------------------------------------------------------------------
        // Check that all the task viability evaluators accepts the task
        // ----------------------------------------------------------------------

        for ( TaskViabilityEvaluator taskViabilityEvaluator : taskViabilityEvaluators )
        {
            Collection<Task> toBeRemoved =
                taskViabilityEvaluator.evaluate( Collections.unmodifiableCollection( queue ) );

            for ( Iterator<Task> it2 = toBeRemoved.iterator(); it2.hasNext(); )
            {
                Task t = it2.next();

                queue.remove( t );
            }
        }

        return true;
    }

    public Task take()
        throws TaskQueueException
    {
        logger.debug( "take" );
        while ( true )
        {
            Task task = dequeue();

            if ( task == null )
            {
                return null;
            }

            for ( TaskExitEvaluator taskExitEvaluator : taskExitEvaluators )
            {
                boolean result = taskExitEvaluator.evaluate( task );

                if ( !result )
                {
                    // the task wasn't accepted; drop it.
                    task = null;

                    break;
                }
            }

            if ( task != null )
            {
                return task;
            }
        }
    }

    public Task poll( int timeout, TimeUnit timeUnit )
        throws InterruptedException
    {
        logger.debug( "pool" );
        return queue.poll( timeout, timeUnit );
    }

    public boolean remove( Task task )
        throws ClassCastException, NullPointerException
    {
        return queue.remove( task );
    }

    public boolean removeAll( List tasks )
        throws ClassCastException, NullPointerException
    {
        return queue.removeAll( tasks );
    }

    // ----------------------------------------------------------------------
    // Queue Inspection
    // ----------------------------------------------------------------------

    public List<Task> getQueueSnapshot()
        throws TaskQueueException
    {
        return Collections.unmodifiableList( new ArrayList( queue ) );
    }

    // ----------------------------------------------------------------------
    // Queue Management
    // ----------------------------------------------------------------------

    private void enqueue( Task task )
    {
        boolean success = queue.add( task );
        logger.debug( "enqueue success {}", success );
    }

    private Task dequeue()
    {
        logger.debug( "dequeue" );
        return queue.poll();
    }

    public List<TaskEntryEvaluator> getTaskEntryEvaluators()
    {
        return taskEntryEvaluators;
    }

    public void setTaskEntryEvaluators( List<TaskEntryEvaluator> taskEntryEvaluators )
    {
        this.taskEntryEvaluators = taskEntryEvaluators;
    }

    public List<TaskExitEvaluator> getTaskExitEvaluators()
    {
        return taskExitEvaluators;
    }

    public void setTaskExitEvaluators( List<TaskExitEvaluator> taskExitEvaluators )
    {
        this.taskExitEvaluators = taskExitEvaluators;
    }

    public List<TaskViabilityEvaluator> getTaskViabilityEvaluators()
    {
        return taskViabilityEvaluators;
    }

    public void setTaskViabilityEvaluators( List<TaskViabilityEvaluator> taskViabilityEvaluators )
    {
        this.taskViabilityEvaluators = taskViabilityEvaluators;
    }
}

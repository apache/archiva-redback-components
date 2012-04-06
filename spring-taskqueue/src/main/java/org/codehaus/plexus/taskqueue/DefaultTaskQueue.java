package org.codehaus.plexus.taskqueue;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * @version $Id$
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

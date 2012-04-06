package org.codehaus.plexus.taskqueue.execution;

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

import junit.framework.TestCase;
import org.codehaus.plexus.taskqueue.BuildProjectTask;
import org.codehaus.plexus.taskqueue.TaskQueue;
import org.codehaus.plexus.taskqueue.TaskQueueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:kenney@apache.org">Kenney Westerhof</a>
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath*:/META-INF/spring-context.xml", "classpath*:/spring-context.xml" } )
public class TaskQueueExecutorTest
    extends TestCase
{
    @Inject
    @Named( value = "taskQueue#default" )
    private TaskQueue taskQueue;

    // inject this to start the executor see @PostConstruct in {@link ThreadedTaskQueueExecutor
    @Inject
    @Named( value = "queueExecutor#default" )
    private TaskQueueExecutor taskQueueExecutor;


    @Test
    public void testTimeoutWithInterrupts()
        throws TaskQueueException, InterruptedException
    {
        BuildProjectTask task = putTask( 2 * 1000, false );

        waitForExpectedTaskEnd( task );

        assertTrue( task.isCancelled() );
        assertFalse( task.isDone() );
    }

    @Test
    public void testTimeoutWithoutInterrupts()
        throws TaskQueueException, InterruptedException
    {
        BuildProjectTask task = putTask( 2 * 1000, true );

        waitForExpectedTaskEnd( task );

        // the thread is killed so the task is neither done nor cancelled
        assertFalse( task.isCancelled() );
        assertFalse( task.isDone() );
    }

    private BuildProjectTask putTask( int executionTime, boolean ignoreInterrupts )
        throws TaskQueueException
    {
        BuildProjectTask task = new BuildProjectTask( 100 );
        task.setMaxExecutionTime( executionTime );
        task.setExecutionTime( 10 * executionTime );
        task.setIgnoreInterrupts( ignoreInterrupts );

        taskQueue.put( task );
        return task;
    }

    private static void waitForExpectedTaskEnd( BuildProjectTask task )
        throws InterruptedException
    {
        // thread scheduling may take some time, so we want to wait until the task
        // is actually running before starting to count the timeout.
        for ( int i = 0; i < 500; i++ )
        {
            if ( task.isStarted() )
            {
                break;
            }
            Thread.sleep( 10 );
        }

        assertTrue( "Task not started in 5 seconds - heavy load?", task.isStarted() );

        Thread.sleep( task.getMaxExecutionTime() );
    }
}

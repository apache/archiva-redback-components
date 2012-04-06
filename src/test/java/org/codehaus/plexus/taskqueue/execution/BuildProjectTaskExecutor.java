package org.codehaus.plexus.taskqueue.execution;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
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

import org.codehaus.plexus.taskqueue.BuildProjectTask;
import org.codehaus.plexus.taskqueue.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author <a href="mailto:kenney@apache.org">Kenney Westerhof</a>
 *
 */
@Service("taskExecutor#build-project")
public class BuildProjectTaskExecutor
    implements TaskExecutor
{

    private Logger logger = LoggerFactory.getLogger( getClass() );

    public void executeTask( Task task0 )
        throws TaskExecutionException
    {
        BuildProjectTask task = (BuildProjectTask) task0;

        task.start();

        logger.info( "Task: " + task + " cancelled: " + task.isCancelled() + "; done: " + task.isDone() );

        long time = System.currentTimeMillis();

        long endTime = task.getExecutionTime() + time;

        for ( long timeToSleep = endTime - time; timeToSleep > 0; timeToSleep = endTime - System.currentTimeMillis() )
        {
            try
            {
                logger.info( "Sleeping " + timeToSleep + "ms (interrupts ignored: " + task.ignoreInterrupts() + ")" );
                Thread.sleep( timeToSleep );

                task.done();

                logger.info( "Task completed normally: " + task + " cancelled: " + task.isCancelled() + "; done: "
                                 + task.isDone() );
            }
            catch ( InterruptedException e )
            {
                if ( !task.ignoreInterrupts() )
                {
                    task.cancel();

                    logger.info(
                        "Task cancelled: " + task + " cancelled: " + task.isCancelled() + "; done: " + task.isDone() );

                    throw new TaskExecutionException( "Never interrupt sleeping threads! :)", e );
                }
                else
                {
                    logger.info( "Ignoring interrupt" );
                }
            }
        }

    }
}

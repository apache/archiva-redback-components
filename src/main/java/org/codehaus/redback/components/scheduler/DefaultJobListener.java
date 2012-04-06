package org.codehaus.redback.components.scheduler;

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
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Currently the role this class plays is set the value of the <code>JobDataMap</code>
 * in the job so that the convenience methods for accessing the logger, context,
 * service broker and configuration will work as expected.
 *
 * @author <a href="mailto:jason@zenplex.com">Jason van Zyl</a>
 * @version $Id$
 */
public class DefaultJobListener
    implements JobListener
{
    /**
     * <p>
     * <p/>
     * Get the name of the <code>JobListener</code>.</p>
     */
    public String getName()
    {
        return "DefaultJobLister";
    }

    /**
     * <p>
     * <p/>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Job}</code>
     * is about to be executed (an associated <code>{@link org.quartz.Trigger}</code> has
     * occured).</p>
     */
    public void jobToBeExecuted( JobExecutionContext context )
    {
        Job job = context.getJobInstance();

        // Only attempt to set the ServiceBroker when we are dealing
        // with subclasses AbstractJob.
        if ( job instanceof AbstractJob )
        {
            ( (AbstractJob) job ).setJobDataMap( context.getJobDetail().getJobDataMap() );
        }
    }

    public void jobExecutionVetoed( JobExecutionContext jobExecutionContext )
    {
    }

    /**
     * <p>
     * <p/>
     * Called by the <code>{@link Scheduler}</code> after a <code>{@link Job}</code>
     * has been executed, and be for the associated <code>Trigger</code>'s
     * <code>triggered(xx)</code> method has been called.</p>
     */
    public void jobWasExecuted( JobExecutionContext context, JobExecutionException jobException )
    {
        Job job = context.getJobInstance();

        // Only attempt to null the ServiceBroker when we are dealing
        // with subclasses AbstractJob.
        if ( job instanceof AbstractJob )
        {
        }
    }
}

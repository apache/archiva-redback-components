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
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.InterruptableJob;
import org.quartz.UnableToInterruptJobException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class from which all <code>Job</code>s running in the
 * scheduler should be derived from if they want access to the
 * ServiceBroker.
 *
 * @author <a href="mailto:jason@zenplex.com">Jason van Zyl</a>
 * @version $Id$
 */
public abstract class AbstractJob
    implements InterruptableJob
{

    private  Logger log = LoggerFactory.getLogger( getClass() );

    /** JobDataMap tag for the job's logger. */
    public static final String LOGGER = "JOB_LOGGER";
    
    /** JobDataMap tag for the job's context. */
    public static final String CONTEXT = "JOB_CONTEXT";

    /** JobDataMap tag for the job's service broker. */
    public static final String SERVICE_MANAGER = "JOB_SERVICE_MANAGER";
    
    /** JobDataMap tag for the job's configuration. */
    public static final String EXECUTION_CONFIGURATION = "JOB_EXECUTION_CONFIGURATION";

    /** Job Data Map */
    private JobDataMap jobDataMap;

    private boolean interrupted;

    /** Set Job Data Map */
    public void setJobDataMap(JobDataMap jobDataMap)
    {
        this.jobDataMap = jobDataMap;
    }
    
    /** Get Job Data Map */
    public JobDataMap getJobDataMap()
    {
        return jobDataMap;
    }        
    
    /** Get the Logger. */
    public Logger getLogger()
    {
        return (Logger) getJobDataMap().get(LOGGER);
    }        



    /** Execute the Job. */
    public abstract void execute(JobExecutionContext context)
        throws JobExecutionException;

    public boolean isInterrupted()
    {
        return interrupted;
    }

    public void interrupt()
        throws UnableToInterruptJobException
    {
        interrupted = true;
    }
}

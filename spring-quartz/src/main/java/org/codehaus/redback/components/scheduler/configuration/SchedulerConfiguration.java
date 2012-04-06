package org.codehaus.redback.components.scheduler.configuration;

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

import org.codehaus.redback.components.scheduler.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Class to represent the configuration file for the proxy
 *
 * @author John Tolentino
 * plexus.component role="org.codehaus.plexus.scheduler.configuration.SchedulerConfiguration"
 */
@Service
public class SchedulerConfiguration
{
    /*
     * plexus.required
     */
    @Inject
    private Scheduler plexusScheduler;

    public String getInstanceName()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME );
    }

    public void setInstanceName( String instanceName )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, instanceName );
    }

    public String getInstanceId()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_INSTANCE_ID );
    }

    public void setInstanceId( String InstanceId )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, InstanceId );
    }

    public String getThreadName()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_THREAD_NAME );
    }

    public void setThreadName( String threadName )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_THREAD_NAME, threadName );
    }

    public String getIdleWaitTime()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_IDLE_WAIT_TIME );
    }

    public void setIdleWaitTime( String idleWaitTime )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_IDLE_WAIT_TIME, idleWaitTime );
    }

    public String getDbFailureRetryInterval()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_DB_FAILURE_RETRY_INTERVAL );
    }

    public void setDbFailureRetryInterval( String dbFailureRetryInterval )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_DB_FAILURE_RETRY_INTERVAL, dbFailureRetryInterval );
    }

    public String getClassLoadHelper()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_CLASS_LOAD_HELPER_CLASS );
    }

    public void setClassLoadHelper( String classLoadHelper )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_CLASS_LOAD_HELPER_CLASS, classLoadHelper );
    }

    public String getContextKey()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_CONTEXT_PREFIX );
    }

    public void setContextKey( String contextKey )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_CONTEXT_PREFIX, contextKey );
    }

    public String getUserTransactionURL()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_USER_TX_URL );
    }

    public void setUserTransactionURL( String userTransactionURL )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_USER_TX_URL, userTransactionURL );
    }

    public String getWrapJobExecutionInUserTransaction()
    {
        return plexusScheduler.getProperties().getProperty( StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX );
    }

    public void setWrapJobExecutionInUserTransaction( String wrapJobExecutionInUserTransaction )
    {
        plexusScheduler.getProperties().setProperty( StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX, wrapJobExecutionInUserTransaction );
    }

}

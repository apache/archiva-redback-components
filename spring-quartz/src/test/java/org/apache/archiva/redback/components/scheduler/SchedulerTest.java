package org.apache.archiva.redback.components.scheduler;

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
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerListener;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( locations = { "classpath*:/META-INF/spring-context.xml", "classpath:/spring-context.xml" } )
public class SchedulerTest
    extends TestCase
    implements TriggerListener
{
    private boolean triggerFired;

    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    private Scheduler scheduler;

    @After
    public void stop()
    {
        scheduler.shutdown( true );
    }

    @Test
    public void testCreation()
        throws Exception
    {

        assertNotNull( scheduler );

        JobDataMap dataMap = new JobDataMap();

        dataMap.put( "project", "continuum" );

        JobDetailImpl jobDetail = new JobDetailImpl( "job", "group", JobOne.class );

        jobDetail.setJobDataMap( dataMap );

        TriggerBuilder.newTrigger();

        Trigger trigger = new SimpleTriggerImpl( "trigger", "group" );

        scheduler.addGlobalTriggerListener( this );

        scheduler.scheduleJob( jobDetail, trigger );

        while ( !triggerFired )
        {
            Thread.sleep( 10 );
        }
        logger.info( "ok triggerFired" );
    }

    public void triggerComplete( Trigger trigger, JobExecutionContext context, int triggerInstructionCode )
    {
    }

    public boolean vetoJobExecution( Trigger trigger, JobExecutionContext context )
    {
        return false;
    }

    public void triggerFired( Trigger trigger, JobExecutionContext context )
    {
        logger.info( "Trigger fired!" );

        triggerFired = true;
    }

    public void triggerMisfired( Trigger trigger )
    {
    }

    public void triggerComplete( Trigger trigger, JobExecutionContext context,
                                 Trigger.CompletedExecutionInstruction triggerInstructionCode )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName()
    {
        return "foo";
    }


}


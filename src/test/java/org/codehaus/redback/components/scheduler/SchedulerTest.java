package org.codehaus.redback.components.scheduler;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {"classpath*:/META-INF/spring-context.xml","classpath:/spring-context.xml"} )
public class SchedulerTest
    extends TestCase
    implements TriggerListener
{
    private boolean triggerFired;

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

        while ( ! triggerFired )
        {
          //System.out.println("! triggerFired");
          Thread.sleep( 10 );
        }
        System.out.println("ok triggerFired");
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
        System.out.println( "Trigger fired!" );

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


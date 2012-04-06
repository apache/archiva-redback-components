package org.codehaus.plexus.taskqueue;

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

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class BuildProjectTask
    implements Task
{
    private boolean passAEntryEvaluator;

    private boolean passBEntryEvaluator;

    private boolean passAExitEvaluator;

    private boolean passBExitEvaluator;

    private long timestamp;

    private long maxExecutionTime;

    private long executionTime;

    private boolean cancelled;

    private boolean done;

    private boolean started;

    private boolean ignoreInterrupts;

    public BuildProjectTask( boolean passAEntryEvaluator, boolean passBEntryEvaluator, boolean passAExitEvaluator,
                             boolean passBExitEvaluator )
    {
        this.passAEntryEvaluator = passAEntryEvaluator;

        this.passBEntryEvaluator = passBEntryEvaluator;

        this.passAExitEvaluator = passAExitEvaluator;

        this.passBExitEvaluator = passBExitEvaluator;
    }

    public BuildProjectTask( long timestamp )
    {
        this( true, true, true, true );

        this.timestamp = timestamp;
    }

    public boolean isPassAEntryEvaluator()
    {
        return passAEntryEvaluator;
    }

    public boolean isPassBEntryEvaluator()
    {
        return passBEntryEvaluator;
    }

    public boolean isPassAExitEvaluator()
    {
        return passAExitEvaluator;
    }

    public boolean isPassBExitEvaluator()
    {
        return passBExitEvaluator;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public long getMaxExecutionTime()
    {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime( long timeout )
    {
        maxExecutionTime = timeout;
    }

    public void setExecutionTime( long l )
    {
        this.executionTime = l;
    }

    public long getExecutionTime()
    {
        return executionTime;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void cancel()
    {
        cancelled = true;
    }

    public void done()
    {
        this.done = true;
    }

    public boolean isDone()
    {
        return done;
    }

    public boolean isStarted()
    {
        return started;
    }

    public void start()
    {
        this.started = true;
    }

    public void setIgnoreInterrupts( boolean ignore )
    {
        this.ignoreInterrupts = ignore;
    }

    public boolean ignoreInterrupts()
    {
        return ignoreInterrupts;
    }

}

package org.apache.archiva.redback.components.evaluator;

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

import java.util.List;

/**
 * ExpressionEvaluator
 *
 * @author <a href="mailto:joakim@erdfelt.com">Joakim Erdfelt</a>
 * @version $Id$
 */
public interface ExpressionEvaluator
{
    /**
     * Add a source for expression resolution.
     *
     * @param source the source to add.
     */
    void addExpressionSource( ExpressionSource source );

    /**
     * Evaluate a string, and expand expressions as needed.
     *
     * @param str the expression
     * @return the value of the expression
     * @throws EvaluatorException if a problem occurs whilst evaluating
     */
    String expand( String str )
        throws EvaluatorException;

    /**
     * Get the List of expression sources.
     *
     * @return the list of expression sources.
     */
    List getExpressionSourceList();

    /**
     * Remove a specific expression source.
     *
     * @param source the source to remove.
     * @return true if expression source was removed.
     */
    boolean removeExpressionSource( ExpressionSource source );
}

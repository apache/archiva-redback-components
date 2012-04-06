package org.codehaus.redback.components.springutils;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * utility class to mimic some behaviour of the plexus container with role#hint
 * @author Olivier Lamy
 */
@Service( "componentContainer" )
public class ComponentContainer
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    private static final String DEFAULT_ROLE_HINT = "default";

    public static final String BEAN_NAME_ROLEHINTSEPARATOR = "#";

    /**
     * To prevent to much use of #buildMapWithRole we store already used values here
     *
     * @see #buildMapWithRole(Class)
     */
    private Map<String, Map<String, ?>> classBeansOfType = new ConcurrentHashMap<String, Map<String, ?>>();

    public String getDefaultRoleHint()
    {
        return DEFAULT_ROLE_HINT;
    }

    @Inject
    protected ApplicationContext applicationContext;

    /**
     * <b>must be used only at startup of container (ie in initialize method as it
     * can cause performance issue http://jira.springframework.org/browse/SPR-5360</b><br/>
     * Returns bean of type T. <br/>
     * <b>It must be unique of not {@link RuntimeException}</b>
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public <T> T getComponent( Class<T> clazz )
    {
        Map<String, T> beansOfType = applicationContext.getBeansOfType( clazz );
        if ( beansOfType == null || beansOfType.isEmpty() )
        {
            throw new RuntimeException( "no beans of Type " + clazz.getName() );
        }
        if ( beansOfType.size() > 1 )
        {
            throw new RuntimeException( "not only one beans of Type " + clazz.getName() );
        }
        return beansOfType.values().iterator().next();
    }

    /**
     * <b>must be used only at startup of container (ie in initialize method as it
     * can cause performance issue http://jira.springframework.org/browse/SPR-5360</b><br/>
     * Returns bean of type T and hint . <br/>
     *
     * @param <T>
     * @param clazz
     * @param hint
     * @return
     */
    public <T> T getComponent( Class<T> clazz, String hint )
    {
        Map<String, T> beansOfType = buildMapWithRole( clazz );
        if ( beansOfType == null || beansOfType.isEmpty() )
        {
            throw new RuntimeException( "no beans of Type " + clazz.getName() );
        }
        T bean = beansOfType.get( hint );
        if ( bean == null )
        {
            throw new RuntimeException( "no beans of Type " + clazz.getName() + " with hint " + hint );
        }
        return bean;
    }

    /**
     * Return true if one and only bean of type T exists.
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public <T> boolean hasComponent( Class<T> clazz )
    {
        Map<String, T> beansOfType = applicationContext.getBeansOfType( clazz );
        if ( beansOfType == null || beansOfType.isEmpty() )
        {
            return false;
        }
        return beansOfType.size() == 1;
    }

    /**
     * Return true if one and only bean of type T and hint exists.
     *
     * @param <T>
     * @param clazz
     * @param hint
     * @return
     */
    public <T> boolean hasComponent( Class<T> clazz, String hint )
    {
        Map<String, T> beansOfType = buildMapWithRole( clazz );
        if ( beansOfType == null || beansOfType.isEmpty() )
        {
            return false;
        }
        return beansOfType.containsKey( hint );
    }

    /**
     * <b>must be used only at startup of container (ie in initialize method as it
     * can cause performance issue http://jira.springframework.org/browse/SPR-5360</b><br/>
     * Produce a map with hint as key and bean as value.<br/>
     * An internal map is used to cache call to #buildMapWithRole
     *
     * @param <T>
     * @param clazz
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public <T> Map<String, T> buildMapWithRole( Class<T> clazz )
    {
        try
        {
            Map<String, T> beansOfType = (Map<String, T>) classBeansOfType.get( clazz.getName() );
            if ( beansOfType == null )
            {
                Map<String, T> map = this.applicationContext.getBeansOfType( clazz );
                beansOfType = buildMapWithRole( map );
                classBeansOfType.put( clazz.getName(), beansOfType );
            }
            return beansOfType;
        }
        catch ( Throwable e )
        {
            log.error( e.getMessage(), e );
            throw new RuntimeException( e.getMessage(), e );
        }
    }


    /**
     * Mimic of lookupMap from plexus. <br/>
     * Ex: if the bean is called "foo#mine" "AvlRqBuilder#1"A
     * then the map will contains mine as key with the bean foo#mine as value </b>
     * <b>if no # in the bean name then the bean name will be as it's returned</b>.
     *
     * @param beansOfType
     * @return
     */
    public static <T> Map<String, T> buildMapWithRole( Map<String, T> beansOfType )
    {
        if ( beansOfType == null || beansOfType.isEmpty() )
        {
            return Collections.emptyMap();
        }
        Map<String, T> beansOfHint = new HashMap<String, T>();
        for ( Map.Entry<String, T> entry : beansOfType.entrySet() )
        {
            int separatorIndex = StringUtils.indexOf( entry.getKey(), '#' );
            if ( separatorIndex >= 0 )
            {
                String hint = entry.getKey().substring( separatorIndex + 1, entry.getKey().length() );
                beansOfHint.put( hint, beansOfType.get( entry.getKey() ) );
            }
            else
            {
                beansOfHint.put( entry.getKey(), beansOfType.get( entry.getKey() ) );
            }
        }
        return beansOfHint;
    }
}

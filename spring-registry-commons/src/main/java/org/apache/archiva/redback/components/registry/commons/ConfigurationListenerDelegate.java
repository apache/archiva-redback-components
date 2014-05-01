package org.apache.archiva.redback.components.registry.commons;

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

import org.apache.archiva.redback.components.registry.Registry;
import org.apache.archiva.redback.components.registry.RegistryListener;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * Commons configuration listener that delegates to the given registry listener.
 */
public class ConfigurationListenerDelegate
    implements ConfigurationListener
{
    /**
     * Delegate listener.
     */
    private RegistryListener listener;

    /**
     * Delegate registry.
     */
    private Registry registry;

    public ConfigurationListenerDelegate( RegistryListener listener, Registry registry )
    {
        this.listener = listener;

        this.registry = registry;
    }

    public void configurationChanged( ConfigurationEvent event )
    {
        if ( event.getPropertyName() != null )
        {
            if ( event.isBeforeUpdate() )
            {
                listener.beforeConfigurationChange( registry, event.getPropertyName(), event.getPropertyValue() );
            }
            else
            {
                listener.afterConfigurationChange( registry, event.getPropertyName(), event.getPropertyValue() );
            }
        }
    }

    // needed to be sure elements are properly removed from the listeners list
    @Override
    public int hashCode()
    {
        return this.listener.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        ConfigurationListenerDelegate delegate = ConfigurationListenerDelegate.class.cast( obj );
        return delegate.listener == this.listener;
    }
}

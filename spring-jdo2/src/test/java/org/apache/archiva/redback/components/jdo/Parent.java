package org.apache.archiva.redback.components.jdo;

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
 * modello:java generated file.
 * 
 * @author <a href="mailto:mattis@inamo.no">Mathias Bjerke</a> 
 *
 */
public class Parent
    implements java.io.Serializable
{

    /**
     * Field id
     */
    private long id = 0;

    /**
     * Field price
     */
    private double price = 0.0;

    /**
     * Field name
     */
    private String name;

    /**
     * Field description
     */
    private String description;

    /**
     * Field children
     */
    private java.util.List children;

    /**
     * Method addChildren
     * 
     * @param child
     */
    public void addChildren( Child child )
    {
        getChildren().add( child );
    }

    /**
     * Method equals
     * 
     * @param other
     */
    public boolean equals( Object other )
    {
        if ( this == other )
        {
            return true;
        }

        if ( !( other instanceof Parent ) )
        {
            return false;
        }

        Parent that = (Parent) other;
        boolean result = true;
        result = result && id == that.id;
        return result;
    }

    /**
     * Method getChildren
     */
    public java.util.List getChildren()
    {
        if ( this.children == null )
        {
            this.children = new java.util.ArrayList();
        }

        return this.children;
    }

    /**
     * Get description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Get id
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Get name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get price
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Method hashCode
     */
    public int hashCode()
    {
        int result = 17;
        result = 37 * result + (int) ( id ^ ( id >>> 32 ) );
        return result;
    }

    /**
     * Method removeChildren
     * 
     * @param child
     */
    public void removeChildren( Child child )
    {
        getChildren().remove( child );
    }

    /**
     * Set children
     * 
     * @param children
     */
    public void setChildren( java.util.List children )
    {
        this.children = children;
    }

    /**
     * Set description
     * 
     * @param description
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * Set id
     * 
     * @param id
     */
    public void setId( long id )
    {
        this.id = id;
    }

    /**
     * Set name
     * 
     * @param name
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Set price
     * 
     * @param price
     */
    public void setPrice( double price )
    {
        this.price = price;
    }

    /**
     * Method toString
     */
    public java.lang.String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append( "id = '" );
        buf.append( getId() + "'" );
        return buf.toString();
    }
}

/*
 * $Id$
 */

package org.codehaus.modello.test.model;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Date;

/**
 * Class Model.
 * 
 * @version $Revision$ $Date$
 */
public class Model implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field name.
     */
    private String name;

    /**
     * Field repository.
     */
    private String repository;

    /**
     * Field numeric.
     */
    private int numeric = 0;

    /**
     * Field defString.
     */
    private String defString = "def";

    /**
     * Field defNumeric.
     */
    private int defNumeric = 8080;

    /**
     * Field defBoolean.
     */
    private boolean defBoolean = true;

    /**
     * Field reference.
     */
    private Reference reference;

    /**
     * Field missingReference.
     */
    private Reference missingReference;

    /**
     * Field emptyReference.
     */
    private EmptyReference emptyReference;

    /**
     * Field listReferences.
     */
    private java.util.List listReferences;

    /**
     * Field setReferences.
     */
    private java.util.Set setReferences;

    /**
     * Field stringReferences.
     */
    private java.util.List stringReferences;

    /**
     * Field map.
     */
    private java.util.Map map;

    /**
     * Field properties.
     */
    private java.util.Properties properties;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addListReference.
     * 
     * @param reference
     */
    public void addListReference(Reference reference)
    {
        if ( !(reference instanceof Reference) )
        {
            throw new ClassCastException( "Model.addListReferences(reference) parameter must be instanceof " + Reference.class.getName() );
        }
        getListReferences().add( reference );
    } //-- void addListReference(Reference) 

    /**
     * Method addMap.
     * 
     * @param key
     * @param value
     */
    public void addMap(Object key, String value)
    {
        getMap().put( key, value );
    } //-- void addMap(Object, String) 

    /**
     * Method addProperty.
     * 
     * @param key
     * @param value
     */
    public void addProperty(String key, String value)
    {
        getProperties().put( key, value );
    } //-- void addProperty(String, String) 

    /**
     * Method addSetReference.
     * 
     * @param reference
     */
    public void addSetReference(Reference reference)
    {
        if ( !(reference instanceof Reference) )
        {
            throw new ClassCastException( "Model.addSetReferences(reference) parameter must be instanceof " + Reference.class.getName() );
        }
        getSetReferences().add( reference );
    } //-- void addSetReference(Reference) 

    /**
     * Method addStringReference.
     * 
     * @param string
     */
    public void addStringReference(String string)
    {
        if ( !(string instanceof String) )
        {
            throw new ClassCastException( "Model.addStringReferences(string) parameter must be instanceof " + String.class.getName() );
        }
        getStringReferences().add( string );
    } //-- void addStringReference(String) 

    /**
     * Get the defNumeric field.
     * 
     * @return int
     */
    public int getDefNumeric()
    {
        return this.defNumeric;
    } //-- int getDefNumeric() 

    /**
     * Get the defString field.
     * 
     * @return String
     */
    public String getDefString()
    {
        return this.defString;
    } //-- String getDefString() 

    /**
     * Get the emptyReference field.
     * 
     * @return EmptyReference
     */
    public EmptyReference getEmptyReference()
    {
        return this.emptyReference;
    } //-- EmptyReference getEmptyReference() 

    /**
     * Method getListReferences.
     * 
     * @return java.util.List
     */
    public java.util.List getListReferences()
    {
        if ( this.listReferences == null )
        {
            this.listReferences = new java.util.ArrayList();
        }
        
        return this.listReferences;
    } //-- java.util.List getListReferences() 

    /**
     * Method getMap.
     * 
     * @return java.util.Map
     */
    public java.util.Map getMap()
    {
        if ( this.map == null )
        {
            this.map = new java.util.HashMap();
        }
        
        return this.map;
    } //-- java.util.Map getMap() 

    /**
     * Get the missingReference field.
     * 
     * @return Reference
     */
    public Reference getMissingReference()
    {
        return this.missingReference;
    } //-- Reference getMissingReference() 

    /**
     * Get the name field.
     * 
     * @return String
     */
    public String getName()
    {
        return this.name;
    } //-- String getName() 

    /**
     * Get the numeric field.
     * 
     * @return int
     */
    public int getNumeric()
    {
        return this.numeric;
    } //-- int getNumeric() 

    /**
     * Method getProperties.
     * 
     * @return java.util.Properties
     */
    public java.util.Properties getProperties()
    {
        if ( this.properties == null )
        {
            this.properties = new java.util.Properties();
        }
        
        return this.properties;
    } //-- java.util.Properties getProperties() 

    /**
     * Get the reference field.
     * 
     * @return Reference
     */
    public Reference getReference()
    {
        return this.reference;
    } //-- Reference getReference() 

    /**
     * Get the repository field.
     * 
     * @return String
     */
    public String getRepository()
    {
        return this.repository;
    } //-- String getRepository() 

    /**
     * Method getSetReferences.
     * 
     * @return java.util.Set
     */
    public java.util.Set getSetReferences()
    {
        if ( this.setReferences == null )
        {
            this.setReferences = new java.util.HashSet();
        }
        
        return this.setReferences;
    } //-- java.util.Set getSetReferences() 

    /**
     * Method getStringReferences.
     * 
     * @return java.util.List
     */
    public java.util.List getStringReferences()
    {
        if ( this.stringReferences == null )
        {
            this.stringReferences = new java.util.ArrayList();
        }
        
        return this.stringReferences;
    } //-- java.util.List getStringReferences() 

    /**
     * Get the defBoolean field.
     * 
     * @return boolean
     */
    public boolean isDefBoolean()
    {
        return this.defBoolean;
    } //-- boolean isDefBoolean() 

    /**
     * Method removeListReference.
     * 
     * @param reference
     */
    public void removeListReference(Reference reference)
    {
        if ( !(reference instanceof Reference) )
        {
            throw new ClassCastException( "Model.removeListReferences(reference) parameter must be instanceof " + Reference.class.getName() );
        }
        getListReferences().remove( reference );
    } //-- void removeListReference(Reference) 

    /**
     * Method removeSetReference.
     * 
     * @param reference
     */
    public void removeSetReference(Reference reference)
    {
        if ( !(reference instanceof Reference) )
        {
            throw new ClassCastException( "Model.removeSetReferences(reference) parameter must be instanceof " + Reference.class.getName() );
        }
        getSetReferences().remove( reference );
    } //-- void removeSetReference(Reference) 

    /**
     * Method removeStringReference.
     * 
     * @param string
     */
    public void removeStringReference(String string)
    {
        if ( !(string instanceof String) )
        {
            throw new ClassCastException( "Model.removeStringReferences(string) parameter must be instanceof " + String.class.getName() );
        }
        getStringReferences().remove( string );
    } //-- void removeStringReference(String) 

    /**
     * Set the defBoolean field.
     * 
     * @param defBoolean
     */
    public void setDefBoolean(boolean defBoolean)
    {
        this.defBoolean = defBoolean;
    } //-- void setDefBoolean(boolean) 

    /**
     * Set the defNumeric field.
     * 
     * @param defNumeric
     */
    public void setDefNumeric(int defNumeric)
    {
        this.defNumeric = defNumeric;
    } //-- void setDefNumeric(int) 

    /**
     * Set the defString field.
     * 
     * @param defString
     */
    public void setDefString(String defString)
    {
        this.defString = defString;
    } //-- void setDefString(String) 

    /**
     * Set the emptyReference field.
     * 
     * @param emptyReference
     */
    public void setEmptyReference(EmptyReference emptyReference)
    {
        this.emptyReference = emptyReference;
    } //-- void setEmptyReference(EmptyReference) 

    /**
     * Set the listReferences field.
     * 
     * @param listReferences
     */
    public void setListReferences(java.util.List listReferences)
    {
        this.listReferences = listReferences;
    } //-- void setListReferences(java.util.List) 

    /**
     * Set the map field.
     * 
     * @param map
     */
    public void setMap(java.util.Map map)
    {
        this.map = map;
    } //-- void setMap(java.util.Map) 

    /**
     * Set the missingReference field.
     * 
     * @param missingReference
     */
    public void setMissingReference(Reference missingReference)
    {
        this.missingReference = missingReference;
    } //-- void setMissingReference(Reference) 

    /**
     * Set the name field.
     * 
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    } //-- void setName(String) 

    /**
     * Set the numeric field.
     * 
     * @param numeric
     */
    public void setNumeric(int numeric)
    {
        this.numeric = numeric;
    } //-- void setNumeric(int) 

    /**
     * Set the properties field.
     * 
     * @param properties
     */
    public void setProperties(java.util.Properties properties)
    {
        this.properties = properties;
    } //-- void setProperties(java.util.Properties) 

    /**
     * Set the reference field.
     * 
     * @param reference
     */
    public void setReference(Reference reference)
    {
        this.reference = reference;
    } //-- void setReference(Reference) 

    /**
     * Set the repository field.
     * 
     * @param repository
     */
    public void setRepository(String repository)
    {
        this.repository = repository;
    } //-- void setRepository(String) 

    /**
     * Set the setReferences field.
     * 
     * @param setReferences
     */
    public void setSetReferences(java.util.Set setReferences)
    {
        this.setReferences = setReferences;
    } //-- void setSetReferences(java.util.Set) 

    /**
     * Set the stringReferences field.
     * 
     * @param stringReferences
     */
    public void setStringReferences(java.util.List stringReferences)
    {
        this.stringReferences = stringReferences;
    } //-- void setStringReferences(java.util.List) 


    private String modelEncoding = "UTF-8";

    /**
     * Set an encoding used for reading/writing the model.
     *
     * @param modelEncoding the encoding used when reading/writing the model.
     */
    public void setModelEncoding( String modelEncoding )
    {
        this.modelEncoding = modelEncoding;
    }

    /**
     * @return the current encoding used when reading/writing this model.
     */
    public String getModelEncoding()
    {
        return modelEncoding;
    }
}

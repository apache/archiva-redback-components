package org.codehaus.modello.test.model.io.registry;

import org.apache.archiva.redback.components.registry.Registry;

// Util imports
import java.util.*;

// Model class imports
import org.codehaus.modello.test.model.Model;
import org.codehaus.modello.test.model.Reference;
import org.codehaus.modello.test.model.EmptyReference;




/**
 * Generate Plexus Registry input mechanism for model 'Model'.
 */
public class ModelRegistryReader
{
    public Model read( Registry registry )
    {
        return readModel( "", registry );
    }

    private Model readModel( String prefix, Registry registry )
    {
        Model value = new Model();

        String name = registry.getString( prefix + "name", value.getName() );
        value.setName( name );
        String repository = registry.getString( prefix + "repository", value.getRepository() );
        value.setRepository( repository );
        int numeric = registry.getInt( prefix + "numeric", value.getNumeric() );
        value.setNumeric( numeric );
        String defString = registry.getString( prefix + "defString", value.getDefString() );
        value.setDefString( defString );
        int defNumeric = registry.getInt( prefix + "defNumeric", value.getDefNumeric() );
        value.setDefNumeric( defNumeric );
        boolean defBoolean = registry.getBoolean( prefix + "defBoolean", value.isDefBoolean() );
        value.setDefBoolean( defBoolean );
        Reference reference = readReference( prefix + "reference.", registry );
        value.setReference( reference );
        Reference missingReference = readReference( prefix + "missingReference.", registry );
        value.setMissingReference( missingReference );
        EmptyReference emptyReference = readEmptyReference( prefix + "emptyReference.", registry );
        value.setEmptyReference( emptyReference );
        java.util.List listReferences = new java.util.ArrayList();
        List listReferencesSubsets = registry.getSubsetList( prefix + "listReferences.listReference" );
        for ( Iterator i = listReferencesSubsets.iterator(); i.hasNext(); )
        {
            Reference v = readReference( "", (Registry) i.next() );
            listReferences.add( v );
        }
        value.setListReferences( listReferences );
        java.util.Set setReferences = new java.util.HashSet();
        List setReferencesSubsets = registry.getSubsetList( prefix + "setReferences.setReference" );
        for ( Iterator i = setReferencesSubsets.iterator(); i.hasNext(); )
        {
            Reference v = readReference( "", (Registry) i.next() );
            setReferences.add( v );
        }
        value.setSetReferences( setReferences );
        java.util.List stringReferences = new java.util.ArrayList();
        stringReferences.addAll( registry.getList( prefix + "stringReferences.stringReference" ) );
        value.setStringReferences( stringReferences );
        java.util.Map map = registry.getProperties( prefix + "map" );
        value.setMap( map );
        java.util.Properties properties = registry.getProperties( prefix + "properties" );
        value.setProperties( properties );

        return value;
    }
    
    private Reference readReference( String prefix, Registry registry )
    {
        Reference value = new Reference();

        String name = registry.getString( prefix + "name", value.getName() );
        value.setName( name );

        return value;
    }
    
    private EmptyReference readEmptyReference( String prefix, Registry registry )
    {
        EmptyReference value = new EmptyReference();


        return value;
    }
    
}

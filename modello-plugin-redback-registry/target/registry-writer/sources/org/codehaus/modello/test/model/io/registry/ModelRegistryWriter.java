package org.codehaus.modello.test.model.io.registry;

import org.apache.archiva.redback.components.registry.Registry;

// Util imports
import java.util.*;

// Model class imports
import org.codehaus.modello.test.model.Model;
import org.codehaus.modello.test.model.Reference;
import org.codehaus.modello.test.model.EmptyReference;




/**
 * Generate Plexus Registry output mechanism for model 'Model'.
 */
public class ModelRegistryWriter
{
    public void write( Model model, Registry registry )
    {
        writeModel( "", model, registry );
    }

    private void writeModel( String prefix, Model value, Registry registry )
    {
        if ( value != null )
        {
            if ( value.getName() != null
 )
            {
                String name = "name";
                registry.setString( prefix + name, value.getName() );
            }
            if ( value.getRepository() != null
 )
            {
                String name = "repository";
                registry.setString( prefix + name, value.getRepository() );
            }
            if ( value.getNumeric() != 0
 )
            {
                String name = "numeric";
                registry.setInt( prefix + name, value.getNumeric() );
            }
            if ( value.getDefString() != null && !value.getDefString().equals( "def" )
 )
            {
                String name = "defString";
                registry.setString( prefix + name, value.getDefString() );
            }
            if ( value.getDefNumeric() != 8080
 )
            {
                String name = "defNumeric";
                registry.setInt( prefix + name, value.getDefNumeric() );
            }
            if ( value.isDefBoolean() != true
 )
            {
                String name = "defBoolean";
                registry.setBoolean( prefix + name, value.isDefBoolean() );
            }
            if ( value.getReference() != null
 )
            {
                writeReference( prefix + "reference.", value.getReference(), registry );
            }
            if ( value.getMissingReference() != null
 )
            {
                writeReference( prefix + "missingReference.", value.getMissingReference(), registry );
            }
            if ( value.getEmptyReference() != null
 )
            {
                writeEmptyReference( prefix + "emptyReference.", value.getEmptyReference(), registry );
            }
            if ( value.getListReferences() != null && value.getListReferences().size() > 0
 )
            {
                registry.removeSubset( prefix + "listReferences" );

                int count = 0;
                for ( Iterator iter = value.getListReferences().iterator(); iter.hasNext(); count++ )
                {
                    String name = "listReferences.listReference(" + count + ")";
                    Reference o = ( Reference ) iter.next();
                    writeReference( prefix + name + ".", o, registry );
                }
            }
            if ( value.getSetReferences() != null && value.getSetReferences().size() > 0
 )
            {
                registry.removeSubset( prefix + "setReferences" );

                int count = 0;
                for ( Iterator iter = value.getSetReferences().iterator(); iter.hasNext(); count++ )
                {
                    String name = "setReferences.setReference(" + count + ")";
                    Reference o = ( Reference ) iter.next();
                    writeReference( prefix + name + ".", o, registry );
                }
            }
            if ( value.getStringReferences() != null && value.getStringReferences().size() > 0
 )
            {
                registry.removeSubset( prefix + "stringReferences" );

                int count = 0;
                for ( Iterator iter = value.getStringReferences().iterator(); iter.hasNext(); count++ )
                {
                    String name = "stringReferences.stringReference(" + count + ")";
                    String stringReference = ( String ) iter.next();
                    registry.setString( prefix + name, stringReference );
                }
            }
            if ( value.getMap() != null && value.getMap().size() > 0
 )
            {
                registry.removeSubset( prefix + "map" );
                
                for ( Iterator iter = value.getMap().keySet().iterator(); iter.hasNext(); )
                {
                    String key = (String) iter.next();
                    String v = (String) value.getMap().get( key );

                    registry.setString( prefix + "map." + key, v );
                }
            }
            if ( value.getProperties() != null && value.getProperties().size() > 0
 )
            {
                registry.removeSubset( prefix + "properties" );
                
                for ( Iterator iter = value.getProperties().keySet().iterator(); iter.hasNext(); )
                {
                    String key = (String) iter.next();
                    String v = (String) value.getProperties().get( key );

                    registry.setString( prefix + "properties." + key, v );
                }
            }
        }
    }
    
    private void writeReference( String prefix, Reference value, Registry registry )
    {
        if ( value != null )
        {
            if ( value.getName() != null
 )
            {
                String name = "name";
                registry.setString( prefix + name, value.getName() );
            }
        }
    }
    
    private void writeEmptyReference( String prefix, EmptyReference value, Registry registry )
    {
        if ( value != null )
        {
        }
    }
    
}
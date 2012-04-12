/*
 * $Id$
 */

package org.codehaus.modello.test.model;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Date;

/**
 * Class EmptyReference.
 * 
 * @version $Revision$ $Date$
 */
public class EmptyReference implements java.io.Serializable {


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

package org.codehaus.plexus.msn;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id$
 */
public class MsnException
    extends Exception
{
    public MsnException( String message )
    {
        super( message );
    }

    public MsnException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}

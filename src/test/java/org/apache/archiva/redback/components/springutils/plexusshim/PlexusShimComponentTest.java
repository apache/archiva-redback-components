package org.apache.archiva.redback.components.springutils.plexusshim;

import junit.framework.Assert;
import org.apache.maven.index.NexusIndexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * @author Olivier Lamy
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(
    locations = { "classpath*:/META-INF/spring-context.xml" } )
public class PlexusShimComponentTest
{

    @Inject
    private PlexusShimComponent plexusShimComponent;

    @Test
    public void testMavenIndexerLoad()
        throws Exception
    {
        NexusIndexer nexusIndexer = plexusShimComponent.lookup( NexusIndexer.class );
        Assert.assertNotNull( nexusIndexer );
    }
}

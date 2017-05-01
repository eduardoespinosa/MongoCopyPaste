import java.io.File;

import org.junit.Before;
import org.junit.Test;

import es.eduardoespinosa.MongoCopyPaste;

/**
 * Created by eduardo on 1/05/17.
 */
public class TestMongoCopyPaste {

    MongoCopyPaste mcp;

    @Before
    void setUp(){
        mcp = new MongoCopyPaste();
    }

    @Test
    void testDoCopy(){
        mcp.setOrigin(mcp.getMongo("remote", 27017, "", ""));
        mcp.setTarget(mcp.getMongo("localhost", 27017, "", ""));
        mcp.setDataBasesCollections(new File("src/main/resources/mongoDataBasesCollections.json"));
    }

}

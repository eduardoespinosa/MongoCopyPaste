package es.eduardoespinosa;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by eduardo on 1/05/17.
 */
public class TestMongoCopyPaste {

    MongoCopyPaste mcp;

    @Before
    public void setUp(){
        mcp = new MongoCopyPaste();
    }

    @Test
    public void testDoCopyInAllCollections(){
        // replace with proper values
        mcp.setOrigin(mcp.getMongo("remote", 27017, "", ""));
        mcp.setTarget(mcp.getMongo("localhost", 27017, "", ""));
        mcp.setQuery("{someKey : 'someValue'}");
        mcp.doCopy();
    }
    @Test
    public void testDoCopyAtListedCollections(){
        // replace with proper values
        mcp.setOrigin(mcp.getMongo("remote", 27017, "", ""));
        mcp.setTarget(mcp.getMongo("localhost", 27017, "", ""));
        mcp.setDataBasesCollections(new File("src/main/resources/mongoDataBasesCollections.json"));
        mcp.setQuery("{someKey : 'someValue'}");
        mcp.doCopy();
    }

    @Test
    public void testSetup(){
        mcp.setUp(new File("src/test/resources/mongoCopyPasteSetUp.json"));
        mcp.doCopy();
    }
}

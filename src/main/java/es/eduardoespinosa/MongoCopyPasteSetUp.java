package es.eduardoespinosa;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eduardo on 2/05/17.
 */
public class MongoCopyPasteSetUp {

    private Server origin;
    private Server target;
    private HashMap<String, ArrayList<String>> dataBasesCollections;
    private String query;

    public Server getOrigin() {
        return origin;
    }

    public void setOrigin(Server origin) {
        this.origin = origin;
    }

    public Server getTarget() {
        return target;
    }

    public void setTarget(Server target) {
        this.target = target;
    }

    public HashMap<String, ArrayList<String>> getDataBasesCollections() {
        return dataBasesCollections;
    }

    public void setDataBasesCollections(HashMap<String, ArrayList<String>> dataBasesCollections) {
        this.dataBasesCollections = dataBasesCollections;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

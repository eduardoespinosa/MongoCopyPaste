package es.eduardoespinosa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eduardo on 29/04/17.
 */
public class MongoCopyPaste {

	private MongoClient origin;
	private MongoClient target;
	private HashMap<String, ArrayList<String>> dataBasesCollections;
	private String query;

	public MongoClient getOrigin() {
		return origin;
	}

	public void setOrigin(MongoClient origin) {
		this.origin = origin;
	}

	public MongoClient getTarget() {
		return target;
	}

	public void setTarget(MongoClient target) {
		this.target = target;
	}

	public HashMap<String, ArrayList<String>> getDataBasesCollections() {
		return dataBasesCollections;
	}

	public void setDataBasesCollections(File file) {
		//TODO: Change implementation to call JSONAbstract whe it has MapType support
		ObjectMapper objectMapper = new ObjectMapper();
		MapType dc = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, ArrayList.class);

		try {
			this.dataBasesCollections = objectMapper.readValue(file, dc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDataBasesCollections(String string) {
		ObjectMapper objectMapper = new ObjectMapper();
		MapType dc = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, ArrayList.class);

		try {
			this.dataBasesCollections = objectMapper.readValue(string, dc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	private void buildDataBasesCollections() {
		this.dataBasesCollections = new HashMap<>();
		MongoIterable<String> databaseNames = this.origin.listDatabaseNames();
		databaseNames.iterator().forEachRemaining(db -> {
			MongoIterable<String> collectionsNames = this.origin.getDatabase(db).listCollectionNames();
			ArrayList<String> collections = new ArrayList<String>();
			collectionsNames.iterator().forEachRemaining(collections::add);
			dataBasesCollections.put(db, collections);
		});
	}

	public MongoClient getMongo(String server, Integer port, String user, String pass) {
		MongoClient mongo = null;

		if (!user.equals("") && !pass.equals("")) {
			mongo = new MongoClient(new MongoClientURI("mongodb://" + user + ":" + pass + "@" + server + ":" + port));
		} else {
			mongo = new MongoClient(server, port);
		}
		return mongo;
	}

	private FindIterable<Document> getCursor(MongoClient mongo, String database, String collection) {
		MongoCollection dbCollection = getCollection(mongo, database, collection);
		return dbCollection.find(BasicDBObject.parse(this.query));
	}

	private MongoCollection<Document> getCollection(MongoClient mongo, String database, String collection) {
		return mongo.getDatabase(database).getCollection(collection);
	}

	private List<Document> getDocuments(MongoClient mongo, String database, String collection) {
		List<Document> documents = new ArrayList<>();
		this.getCursor(mongo, database, collection).iterator().forEachRemaining(documents::add);
		return documents;
	}

	private void saveDocuments(MongoClient mongo, String database, String collection, List<Document> documents) {
		if (!documents.isEmpty()) {
			try {
				this.getCollection(mongo, database, collection).insertMany(documents);
			} catch (MongoBulkWriteException e) {
				System.err.println(e);
			}
		}
	}

	public void doCopy() {
		if (null == dataBasesCollections) {
			buildDataBasesCollections();
		}

		this.dataBasesCollections
				.forEach((database, collections) -> collections.forEach(collection -> saveDocuments(this.target,
						database, collection, getDocuments(this.origin, database, collection))));
	}

}

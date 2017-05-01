MongoCopyPaste

Search in one or many collections with a mongo query at original server.
Copy and paste the results in target server. Store the documents in the same structure of origin.

Set up MCP wih following params:
 - origin: Source server. Mandatory.
 - target: Destiny server. Mandatory.
 - databaseCollections: Map of databases and collections. Optional.
    If it is not setup MCP look in all collections for the requested query.
 - query: Mongo Query. Mandatory.

Run:
   doCopy: ask for results and made the copy.

Fun!

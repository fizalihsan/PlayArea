
# MongoDb

* Abides by ACID properties at single-document level only.

## Data modelling

### Embedded document

* **Pros**

* Locality. Less disk seeks and hence faster.
* Atomicity & Isolation during mutant operations.

* **Cons**

* Querying for all sub-documents with a matching condition would return the sub-document along with parent as well. Major drawback of this approach is that we get back much more data than we actually need.
* For example, in a document like below, querying for all the comments by John would return all the books where John has commented, not just the comments. Also it is not possible to sort or limit the comments returned.

``` json
{
	"book": "Head First Java",
	"text": "This is a book",
	"comments": [
		{"author": "John", "text": "..."},
		{"author": "Jack", "text": "..."},
		{"author": "John", "text": "..."},
	]
}
```

* Embedding carries significant penalties with it:
  * The larger a document is, the more RAM it uses.
  * Growing documents must eventually be copied to larger spaces. As you append to the large document, eventually MongoDB is  going to need to move the document to an area with more space available. This movement, when it happens, significantly  slows update performance
  * MongoDB documents have a hard size limit of 16 MB.

* When to use
  * If your application’s query pattern is well-known and data tends to be accessed in only one way, an embedded approach works well.

### Referenced document

* Pros
  * Flexibility
  * Best fit for many-to-many relationships.
* Cons
  * No joins hence needs multiple network calls to retrieve complete data.
  * There is no multi-document transaction in Mongodb. In other words, unlike SQL you cannot edit/delete multiple documents in a single transaction. If a business entity spans across multiple collections, you cannot alter that entity from different collections atomically.
* When to use
  * If your application may query data in many different ways, or you are not able to anticipate the patterns in which data may be queried, a more “normalized” approach may be better.
  * Another factor that may weigh in favor of a more normalized model using document references is when you have one-to-many relationships with very high or unpredictable *arity*.

## Advanced Concepts
		
### Why no transactions?

* MongoDB was designed from the ground up to be easy to scale to multiple	distributed servers. Two of the biggest problems in distributed database design are distributed join operations and distributed transactions.
* Both of these operations are complex to implement, and can yield poor performance or even downtime in the event that a server becomes unreachable. By “punting” on these problems and not supporting
* joins or multidocument transactions at all, MongoDB has been able to implement an automatic sharding solution with much better scaling and performance characteristics than you’d normally be stuck with if you had to take relational joins and transactions into account.

### Write concern

* MongoDB has a configurable write concern. This capability allows you to balance the importance of guaranteeing that all writes are fully recorded in the database with the speed of the insert. 
* For example, if you issue writes to MongoDB and do not require that the database issue any response, the write operations will return very fast (since the application needs to wait for a response from the database) but you cannot be certain that all writes succeeded. Conversely, if you require that MongoDB acknowledge every write operation, the database will not return as quickly but you can be certain that every item will be present in the database. 
* The proper write concern is often an application-specific decision, and depends on the reporting requirements and uses of your analytics application.

**Insert acknowledgement**

By setting w=0, you do not require that MongoDB acknowledge receipt of the insert. `db.events.insert(event, w=0)`

**Journal write acknowledgement**
If you want to ensure that MongoDB not only acknowledges receipt of a write operation but also commits the write operation to the on-disk journal before returning successfully to the application, you can use the j=True option: `db.events.insert(event, j=True)`.

MongoDB uses an on-disk journal file to persist data before writing the updates back to the “regular” data files. Since journal writes are significantly slower than in-memory updates (which are, in turn, much slower than “regular” data file updates), MongoDB batches up journal writes into “group commits” that occur every 100 ms unless overridden in your server settings. What this means for the application developer is that, on average, any individual writes with j=True will take around 50 ms to complete, which is generally even more time than it would take to replicate the data to another server.

**Replication acknowledgement**
To acknowledge that the data has replicated to two members of the replica set before returning: `db.events.insert(event, w=2)`.

### Index Types

**Right-aligned indexes**

TODO

* Order of columns on the compound index matters
* Use `explain()` to analyze the performance of a query.
* Use the `hint()` method to tell Mongo which index to use.

#### Rules of Index Design

# Any fields that will be queried by *equality* should occur first in the index definition.
* Fields used to sort should occur next in the index definition. If multiple fields are being sorted (such as (last_name, first_name), then they should occur in the same order in the index definition.
* Fields that are queried by range should occur last in the index definition.

# References

* MongoDB Applied Design Patterns

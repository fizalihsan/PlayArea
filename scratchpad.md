

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

# GraphQL example project
```gradle run``` and then POST on localhost:8080/rest/books your queries

Make sure you use the HTTP header ```Content-Type: application/graphql```, otherwise use proper JSON encoding.

## Example queries
Gets a book by id
```
query {
  book (id: "isbn1") {
    isbn
    publishedDate
  }
}
```
Gets all books
```
query {
  allBooks {
    isbn
    publishedDate
  }
}
```
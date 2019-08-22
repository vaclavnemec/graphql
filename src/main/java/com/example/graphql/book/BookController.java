package com.example.graphql.book;

import com.example.graphql.GraphQLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final GraphQLService graphQLService;

    public BookController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostMapping("/rest/books")
    public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
        return new ResponseEntity<>(graphQLService.getGraphQL().execute(query), HttpStatus.OK);
    }

}

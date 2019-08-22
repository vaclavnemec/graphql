package com.example.graphql;

import com.example.graphql.book.AllBooksDataFetcher;
import com.example.graphql.book.BookDataFetcher;
import com.example.graphql.book.BookRepository;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GraphQLConfiguration {

    private final Resource resource;
    private final AllBooksDataFetcher allBooksDataFetcher;
    private final BookDataFetcher bookDataFetcher;
    private final BookRepository bookRepository;

    public GraphQLConfiguration(@Value("classpath:books.graphql") Resource resource,
                                AllBooksDataFetcher allBooksDataFetcher, BookDataFetcher bookDataFetcher, BookRepository bookRepository) {
        this.resource = resource;
        this.allBooksDataFetcher = allBooksDataFetcher;
        this.bookDataFetcher = bookDataFetcher;
        this.bookRepository = bookRepository;
    }

    @Bean
    public GraphQLSchema schema() throws IOException {
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(resource.getFile());
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring
                .newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring
                            .dataFetcher("allBooks", allBooksDataFetcher)
                            .dataFetcher("book", bookDataFetcher)
                ).build();
    }
}

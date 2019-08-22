package com.example.graphql;

import com.example.graphql.book.AllBooksDataFetcher;
import com.example.graphql.book.BookDataFetcher;
import com.example.graphql.book.Book;
import com.example.graphql.book.BookRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    private final Resource resource;
    private GraphQL graphQL;

    private final AllBooksDataFetcher allBooksDataFetcher;
    private final BookDataFetcher bookDataFetcher;
    private final BookRepository bookRepository;

    public GraphQLService(@Value("classpath:books.graphql") Resource resource,
                          AllBooksDataFetcher allBooksDataFetcher, BookDataFetcher bookDataFetcher, BookRepository bookRepository) {
        this.resource = resource;
        this.allBooksDataFetcher = allBooksDataFetcher;
        this.bookDataFetcher = bookDataFetcher;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void loadSchema() throws IOException {

        loadIntoDb();

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(resource.getFile());
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadIntoDb() {
        bookRepository.saveAll(
                Stream.of(
                        new Book("isbn1", "Freedom fighter",
                                "Mohen", new String[] {"Jurecka"}, new Date()),
                        new Book("isbn2", "Freedom fighter 2",
                                "Mohen", new String[] {"Jurecka"}, new Date()),
                        new Book("isbn3", "Freedom fighter 3",
                                "Mohen", new String[] {"Jurecka"}, new Date()))
                        .collect(Collectors.toList()));
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

    public GraphQL getGraphQL() {
        return graphQL;
    }
}

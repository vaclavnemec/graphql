package com.example.graphql.book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookDataFetcher implements DataFetcher<Optional<Book>> {

    private final BookRepository bookRepository;

    public BookDataFetcher(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> get(DataFetchingEnvironment environment) {
        return bookRepository.findById(environment.getArgument("id"));
    }
}

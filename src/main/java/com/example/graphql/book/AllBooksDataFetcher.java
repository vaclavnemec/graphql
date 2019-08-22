package com.example.graphql.book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AllBooksDataFetcher implements DataFetcher<Collection<Book>> {

    private final BookRepository bookRepository;

    public AllBooksDataFetcher(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<Book> get(DataFetchingEnvironment environment) {
        return this.bookRepository.findAll();
    }
}

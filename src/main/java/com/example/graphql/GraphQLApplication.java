package com.example.graphql;

import com.example.graphql.book.Book;
import com.example.graphql.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class GraphQLApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQLApplication.class, args);
	}

	@Autowired
	BookRepository bookRepository;

	@Bean
	CommandLineRunner loadIntoDb() {
		return runner -> bookRepository.saveAll(
				Stream.of(
						new Book("isbn1", "Freedom fighter",
								"Mohen", new String[] {"Jurecka"}, new Date()),
						new Book("isbn2", "Freedom fighter 2",
								"Mohen", new String[] {"Jurecka"}, new Date()),
						new Book("isbn3", "Freedom fighter 3",
								"Mohen", new String[] {"Jurecka"}, new Date()))
						.collect(Collectors.toList()));
	}
}

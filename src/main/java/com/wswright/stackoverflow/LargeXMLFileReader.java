package com.wswright.stackoverflow;

import org.codehaus.stax2.XMLInputFactory2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SpringBootApplication(scanBasePackages = {
		"com.wswright.stackoverflow.config",
		"com.wswright.stackoverflow.controller",
		"com.wswright.stackoverflow.model",
		"com.wswright.stackoverflow.repository",
		"com.wswright.stackoverflow.service"
})
@EnableWebFlux
@EnableR2dbcRepositories(basePackages = {"com.wswright.stackoverflow.model"})
public class LargeXMLFileReader {



	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LargeXMLFileReader.class, args);
//		final Pattern pattern = Pattern.compile("while \\(scanner\\.hasNext\\(\\)\\) \\{");
//		Instant start = Instant.now();
//		System.out.println("The source of this content is from the Stack Exchange Network. https://StackExchange.com");
//		System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
//		System.out.println("Calculating # of posts containing '" + pattern.pattern() + "'...");
////		System.out.println("Posts found: " + getPostsContaining(pattern));
//		printFirstNPosts(10);

//		parseFile(FILE_NAME);


//		Instant stop = Instant.now();
//		Duration duration = Duration.between(start,stop);
//		System.out.print(String.format("It took %dh:%dm:%ds:%dms",
//				duration.toHoursPart(),
//				duration.toMinutesPart(),
//				duration.toSecondsPart(),
//				duration.toMillisPart()));
	}
}

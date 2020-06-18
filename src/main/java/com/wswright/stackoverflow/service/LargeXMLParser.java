package com.wswright.stackoverflow.service;

import com.wswright.stackoverflow.config.AppConfig;
import com.wswright.stackoverflow.model.Post;
import com.wswright.stackoverflow.repository.ReactivePostRepository;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.stax2.XMLInputFactory2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Service
public class LargeXMLParser {

	private final AppConfig config;
	private final ReactivePostRepository repo;

	public LargeXMLParser(AppConfig config, ReactivePostRepository repo) {
		this.config = config;
		this.repo = repo;
	}

	public Stream<String> getRawPosts() {
		try {
			return Files.lines(Paths.get(config.getXMLFilename())).skip(2);
		} catch (IOException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}

	public long getPostsContaining(Pattern p) {
		return getRawPosts().filter(post -> p.matcher(post).find()).count();
	}

	public void printFirstNPosts(int n) {
		getRawPosts().limit(n).forEach(post -> System.out.println(String.format("Post: %s", post)));
	}

	@PostConstruct
	public void parse() {
		log.debug("Skipping XML File Import.");
//		parseFile(config.getXMLFilename());
	}

	public void parseFile(String filename) {
		Map<String, Integer> uniqueAttributes = new HashMap<>();
		Map<String, Integer> attributeCounts = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		try {
			XMLInputFactory2 xmlInputFactory2 = (XMLInputFactory2) XMLInputFactory2.newInstance();
			xmlInputFactory2.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
			xmlInputFactory2.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
			xmlInputFactory2.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
			xmlInputFactory2.configureForSpeed();

			long elementCount = 0;
			String curElement = "";
			final XMLStreamReader xmlStreamReader = xmlInputFactory2.createXMLStreamReader(filename, new FileInputStream(filename));
			final int BATCH_SIZE = 10000;
			int elementNum = 0;
			List<Post> batch = new ArrayList<>();
			while(xmlStreamReader.hasNext()) {
				int xmlEvent = xmlStreamReader.next();
//				if(++elementCount > 1000000)
//					return;
				switch (xmlEvent) {
//					case XMLEvent.START_DOCUMENT -> log.info("Parsing...");
//					case XMLEvent.END_DOCUMENT -> log.info("Finished parsing.");
					case XMLEvent.START_ELEMENT -> {
						if(!xmlStreamReader.getName().toString().equals("row"))
							break;
						final int attrCount = xmlStreamReader.getAttributeCount();
						Post p = new Post();
						for(int i=0; i<attrCount; i++) {
							final String attributeName = xmlStreamReader.getAttributeName(i).toString();
							final String attributeValue = xmlStreamReader.getAttributeValue(i);
							switch (attributeName) {
								case "Id" -> {
									p.setId(Long.parseLong(attributeValue));

								}
								case "PostTypeId" -> p.setPostTypeId(Integer.parseInt(attributeValue));
								case "AcceptedAnswerId" -> p.setAcceptedAnswerId(Integer.parseInt(attributeValue));
								case "CreationDate" -> p.setCreationDate(sdf.parse(attributeValue));
								case "Score" -> p.setScore(attributeValue);
								case "ViewCount" -> p.setViewCount(Long.parseLong(attributeValue));
								case "Body" -> p.setBody(attributeValue);
								case "OwnerUserId" -> p.setOwnerUserId(attributeValue);
								case "LastEditorUserId" -> p.setLastEditorUserId(attributeValue);
								case "LastEditorDisplayName" -> p.setLastEditorDisplayName(attributeValue);
								case "LastEditDate" -> p.setLastEditDate(sdf.parse(attributeValue));
								case "LastActivityDate" -> p.setLastActivityDate(sdf.parse(attributeValue));
								case "Title" -> p.setTitle(attributeValue);
								case "Tags" -> p.setTags(attributeValue);
								case "AnswerCount" -> p.setAnswerCount(Integer.parseInt(attributeValue));
								case "CommentCount" -> p.setCommentCount(Integer.parseInt(attributeValue));
								case "FavoriteCount" -> p.setFavoriteCount(Integer.parseInt(attributeValue));
								case "CommunityOwnedDate" -> p.setCommunityOwnedDate(sdf.parse(attributeValue));
								case "ParentId" -> p.setParentId(attributeValue);
								case "OwnerDisplayName" -> p.setOwnerDisplayName(attributeValue);
								case "ClosedDate" -> p.setClosedDate(sdf.parse(attributeValue));
							}
						}
						batch.add(p);
						elementNum++;
						if(elementNum >= BATCH_SIZE) {
							Instant start = Instant.now();
							repo.saveAll(batch);
							batch.clear();
							elementNum = 0;
							Duration duration = Duration.between(start,Instant.now());
//							log.info(String.format("Saved %d posts in %ds:%dms! Date: %s", BATCH_SIZE,duration.toSecondsPart(), duration.toMillisPart(), p.getCreationDate()));
						}
					}
				}
			}
			if(!batch.isEmpty()) {
				repo.saveAll(batch);
				batch.clear();
			}
		} catch (FactoryConfigurationError | FileNotFoundException | XMLStreamException | ParseException factoryConfigurationError) {
			factoryConfigurationError.printStackTrace();
			System.out.println(String.format("Error occurred when trying to parse the file! Filename: %s", filename));
		}
		System.out.println("Done!");
	}
}

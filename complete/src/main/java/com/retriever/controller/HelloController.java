package com.retriever.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.retriever.model.Document;
import com.retriever.model.Documents;

@RestController
public class HelloController {

    @RequestMapping("/")

    public String index(@RequestParam(defaultValue="troll" ) String searchstring ) {
		List<Document> alldocuments = getAllhits();
		List<Document> allHits = alldocuments.stream().filter(
				d -> d.getStory() == null ? false : d.getStory().contains(searchstring))
				.collect(Collectors.toList());

		allHits.stream().forEach(
				d -> d.setStory(d.getStory().replaceAll(searchstring,
						"<B>" + searchstring + "</B>")));

		return allHits.toString();

    }

	private List<Document> getAllhits() {
		RestTemplate restTemplate = new RestTemplate();
		List<Document> documents = restTemplate.getForObject(
				"https://www.retriever-info.com/doccyexample/documents.json",
				Documents.class).getDocument();
		return documents;
	}

}

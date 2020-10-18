package com.example.moviesearch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.moviesearch.config.SearchBuilder;
import com.example.moviesearch.model.PlayData;
import com.example.moviesearch.repository.PlayDataRepository;
import com.example.moviesearch.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;


@RestController
@CrossOrigin
@RequestMapping("/search")
public class MovieSearchController {
	
	private Logger logger = LoggerFactory.getLogger(MovieSearchController.class);

	@Value("${play.url}")
	String playUrl;
	
	@Autowired
    	private SearchBuilder searchBuilder;


	@Autowired
	private ElasticsearchOperations operations;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PlayDataRepository playDataRepository;

    @GetMapping(value = "/{searchText}")
    public ResponseEntity<?> getAll(@PathVariable final String searchText) {
    	
    	ApiSuccessResponse response = new ApiSuccessResponse();
    	response.setError(false);
    	response.setHttpStatus("Success");
    	response.setHttpStatusCode(200);
    	response.setMessage("Sucessfully Fetched Data");
    	response.setBody(searchBuilder.getAll(searchText));
    	
    	
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id")
    public void consume(String message) {
     logger.info("Consumed message: " + message);
     operations.putMapping(PlayData.class);
		System.out.println("Loading Data");

		logger.info("Entered into Loading Data services");

		ApiSuccessResponse response = restTemplate
				.exchange(playUrl, HttpMethod.GET, null, ApiSuccessResponse.class)
				.getBody();

		logger.info(response.getBody().toString());

		List<PlayData> play = objectMapper.convertValue(response.getBody(), new TypeReference<List<PlayData>>() {
		});

		playDataRepository.saveAll(play);
		
		logger.info(play.toString());

		System.out.printf("Loading Completed");
    }

	
	
//	@GetMapping("/hello")
//	public ResponseEntity<?> searchMovies(){
//		return ResponseEntity.status(HttpStatus.OK).body(playDataRepository.findAll());
//	}

}

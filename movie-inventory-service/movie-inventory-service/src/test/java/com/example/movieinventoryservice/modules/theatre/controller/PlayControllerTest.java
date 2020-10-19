package com.example.movieinventoryservice.modules.theatre.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.repository.MovieRepository;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.modules.movies.service.serviceImpl.MovieServiceImpl;
import com.example.movieinventoryservice.modules.theatre.repository.AddressRepository;
import com.example.movieinventoryservice.modules.theatre.repository.LocationRepository;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.PlayServiceImpl;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.ScreenServiceImpl;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.TheatreServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ PlayController.class, PlayServiceImpl.class, ScreenServiceImpl.class, TheatreServiceImpl.class, MovieServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class PlayControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private MovieService movieService;
	
	@MockBean
	private TheatreRepository theatreRepository;
	
	public ApiSuccessResponse apiResponse;

	@Autowired
	private PlayService playService;
	
	@MockBean
	private ScreenRepository screenRepository;

	@MockBean
	private PlayRepository playRepository;
	
	@MockBean
	private AddressRepository addressRepository;
	
	@MockBean
	private LocationRepository locationRepository;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@MockBean
	private CastRepository castRepository;

	@MockBean
	private GenreRepository genreRepository;
	
	@MockBean
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		List<Play> play = new ArrayList<>();
		play.add(getPlay());
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenReturn(getPlay());
//		Mockito.verify(playRepository).deleteById(Mockito.anyInt());
		Mockito.when(playRepository.getOne(Mockito.anyInt())).thenReturn(getPlay());
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getPlay()));
		Mockito.when(playRepository.findAll()).thenReturn(play);
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getScreen()));
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Mockito.when(movieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getMovie()));
	
		Mockito.when(restTemplate.exchange(
	            Matchers.anyString(),
	            Matchers.eq(HttpMethod.GET),
	            Matchers.<HttpEntity> any(),
	            Matchers.<Class<String>>any())
	        ).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Hello"));
	
	}

	@Test
	public void testGetPlay() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));
	}

	@Test
	public void testGetPlayException() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetPlayException1() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllPlay() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));
	}

	@Test
	public void testGetAllPlayException() throws Exception {
		Mockito.when(playRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllPlayException1() throws Exception {
		Mockito.when(playRepository.findAll()).thenReturn(new ArrayList<Play>());
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddPlay() throws Exception {
		MvcResult result = mockMvc.perform(post("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));

	}

	@Test
	public void testAddPlayException() throws Exception {

		Mockito.when(playRepository.save(Mockito.any(Play.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdatePlay() throws Exception {
		MvcResult result = mockMvc.perform(put("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdatePlayException() throws Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeletePlay() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/play/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getPlaysAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getPlay());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Play getPlay() {

		Play play = new Play();

		play.setId(1234);
		play.setScreen(getScreen());
		play.setMovie(getMovie());
		return play;
	}
	
	public Screen getScreen() {

		Screen screen = new Screen();
		screen.setTotalSeatsPerScreen(110);
		screen.setName("sampleText");
		return screen;
	}
	
	public Theatre getTheatre() {
		Theatre theatre = new Theatre();
		theatre.setId(1);
		theatre.setName("test");
		
		return theatre;
	}
	
	public Movie getMovie() {

		Movie movie = new Movie();

		movie.setName("Aladdin");
		movie.setGenre(getGenreList());
		movie.setCast(getCastList());

		return movie;
	}

	public Genre getGenre() {

		Genre genre = new Genre();

		genre.setName("Action");

		return genre;
	}

	public Cast getCast() {

		Cast cast = new Cast();

		cast.setName("sampleCast");

		return cast;
	}

	public List<Genre> getGenreList() {

		Genre genre = new Genre();

		genre.setName("Action");

		List<Genre> genres = new ArrayList<>();
		genres.add(genre);
		return genres;
	}

	public List<Cast> getCastList() {

		Cast cast = new Cast();

		cast.setName("sampleCast");

		List<Cast> casts = new ArrayList<>();
		casts.add(cast);
		return casts;
	}
	

}


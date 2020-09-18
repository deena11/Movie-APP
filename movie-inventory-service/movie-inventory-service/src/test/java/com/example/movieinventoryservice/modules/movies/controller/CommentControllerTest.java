package com.example.movieinventoryservice.modules.movies.controller;

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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.modules.movies.repository.CommentRepository;
import com.example.movieinventoryservice.modules.movies.service.CommentService;
import com.example.movieinventoryservice.modules.movies.service.serviceImpl.CommentServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ CommentController.class, CommentServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public ApiSuccessResponse apiResponse;

	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentRepository commentRepository;


	@Before
	public void setUp() throws Exception {
		List<Comment> comments = new ArrayList<>();
		comments.add(getComment());
		Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(getComment());
//		Mockito.verify(commentRepository).deleteById(Mockito.anyInt());
		Mockito.when(commentRepository.getOne(Mockito.anyInt())).thenReturn(getComment());
		Mockito.when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getComment()));
		Mockito.when(commentRepository.findAll()).thenReturn(comments);
	}

	@WithMockUser
	@Test
	public void testGetComment() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/comment/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	public void testGetCommentException() throws Exception {
		Mockito.when(commentRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/comment/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetCommentException1() throws Exception {
		Mockito.when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/comment/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllComment() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/comment/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	public void testGetAllCommentException() throws Exception {
		Mockito.when(commentRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/comment/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetAllCommentException1() throws Exception {
		Mockito.when(commentRepository.findAll()).thenReturn(new ArrayList<Comment>());
		MvcResult result = this.mockMvc.perform(get("/comment/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddComment() throws Exception {
		MvcResult result = mockMvc.perform(post("/comment/").contentType(MediaType.APPLICATION_JSON)
				.content(getCommentsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("test"));

	}

	@Test
	public void testAddCommentException() throws Exception {

		Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/comment/").contentType(MediaType.APPLICATION_JSON)
				.content(getCommentsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdateComment() throws Exception {
		MvcResult result = mockMvc.perform(put("/comment/").contentType(MediaType.APPLICATION_JSON)
				.content(getCommentsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdateCommentException() throws Exception {
		Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/comment/").contentType(MediaType.APPLICATION_JSON)
				.content(getCommentsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeleteComment() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/comment/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getCommentsAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getComment());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Comment getComment() {

		Comment comment = new Comment();

		comment.setComments("test");
		comment.setRating(5);

		return comment;
	}
}

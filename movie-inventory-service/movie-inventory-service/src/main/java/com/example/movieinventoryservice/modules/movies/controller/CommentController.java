package com.example.movieinventoryservice.modules.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.service.CommentService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;

	@GetMapping("/{commentId}")
	public ResponseEntity<?> getComment(@PathVariable("commentId") int commentId) throws RecordNotFoundException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(commentService.getCommentById(commentId));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllComment() throws EmptyListException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(commentService.getAllComments());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/")
	public ResponseEntity<?> addComment(@RequestBody Comment comment) throws RecordNotAddedException {

		logger.info(comment.toString());
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully Added Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(commentService.addComment(comment));

		return ResponseEntity.ok(response);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateComment(@RequestBody Comment comment) throws RecordNotUpdatedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(commentService.updateComment(comment));

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) throws RecordNotDeletedException {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage("Successfully fetched Data");
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(commentService.deleteComment(commentId));

		return ResponseEntity.ok(response);
	}

}

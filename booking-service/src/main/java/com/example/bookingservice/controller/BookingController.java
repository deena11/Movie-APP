package com.example.bookingservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.response.ApiErrorResponse;
import com.example.bookingservice.service.BookingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@CrossOrigin
@RequestMapping("/booking")
public class BookingController {

	private Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@PostMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponseBooking", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> addBooking(@RequestBody Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException {
		logger.info("Add booking service Controller is called ");
		Booking bookingDetails = bookingService.addBooking(booking, request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Saved Successfull");
		response.setBody(bookingDetails);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@GetMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
//	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllBooking() throws BookingServiceDaoException {
		logger.info("Get All booking service Controller is called ");
		List<Booking> bookingList = new ArrayList<Booking>();
		bookingList = bookingService.getAllBooking();
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Get all Booking Successfull");
		response.setBody(bookingList);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@GetMapping("/{bookingId}")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> getBookingById(@PathVariable int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException {
		logger.info("Get booking service Controller is called ");
		Booking booking = bookingService.getBookingById(bookingId, request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Get Booking by Id Successfull");
		response.setBody(booking);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@PutMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponseBooking", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> updateBooking(@RequestBody Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException {
		logger.info("Update booking service Controller is called ");
		Booking bookingDetails = bookingService.updateBooking(booking, request);
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Updated Successfull");
		response.setBody(bookingDetails);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	@DeleteMapping("/{bookingId}")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> deleteUser(@PathVariable int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException {
		logger.info("Delete booking service Controller is called ");
		APISuccessResponse response = new APISuccessResponse();
		response.setHttpStatus(HttpStatus.ACCEPTED.toString());
		response.setStatusCode(200);
		response.setMessage("Booking Deleted Successfull");
		response.setBody(bookingService.deleteBooking(bookingId, request));
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(response);
	}

	public ResponseEntity<?> fallBackResponse(int bookingId, HttpServletRequest request) {
		logger.info("Delete booking service Controller is called ");
		ApiErrorResponse response = new ApiErrorResponse();
		response.setHttpStatus(HttpStatus.REQUEST_TIMEOUT);
		response.setHttpStatusCode(408);
		response.setMessage("Service Takes More time than Expected");
		response.setError(true);
		response.setSuccess(false);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(response);
	}

	public ResponseEntity<?> fallBackResponseBooking(Booking booking, HttpServletRequest request) {
		logger.info("Delete booking service Controller is called ");
		ApiErrorResponse response = new ApiErrorResponse();
		response.setHttpStatus(HttpStatus.REQUEST_TIMEOUT);
		response.setHttpStatusCode(408);
		response.setMessage("Service Takes More time than Expected");
		response.setError(true);
		response.setSuccess(false);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(response);
	}

	public ResponseEntity<?> fallBackResponse() {
		logger.info("Delete booking service Controller is called ");
		ApiErrorResponse response = new ApiErrorResponse();
		response.setHttpStatus(HttpStatus.REQUEST_TIMEOUT);
		response.setHttpStatusCode(408);
		response.setMessage("Service Takes More time than Expected");
		response.setError(true);
		response.setSuccess(false);
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(response);
	}
}

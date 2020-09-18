package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {
	
	private Logger logger = LoggerFactory.getLogger(ScreenServiceImpl.class);
	
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private TheatreService theatreService;
	
	private String message= "";

	@Override
	public Screen addScreen(Screen screen) throws RecordNotAddedException {
		try {
			logger.info(screen.toString());
			screen.setTheatre(theatreService.getTheatreById(screen.getTheatre().getId()));
			return screenRepository.save(screen);
		} catch (DataAccessException | RecordNotFoundException ex) {
			logger.info(ex.getLocalizedMessage());
			throw new RecordNotAddedException("Failed To Add Screen", ex.getCause());
		}
	}

	@Override
	public String deleteScreen(int screenId) throws RecordNotDeletedException {
		try {
			screenRepository.deleteById(screenId);
			message = "Successfully deleted Screen of id - " + screenId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updateScreen(Screen screen) throws RecordNotUpdatedException {
		try {

			screenRepository.save(screen);
			message = "Successfully updated Screen";
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Screen getScreenById(int screenId) throws RecordNotFoundException {
		try {
			Optional<Screen> screen = screenRepository.findById(screenId);
			if (screen.isPresent()) {
				return screen.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Screen> getAllScreens() throws EmptyListException {
		try {
			List<Screen> screens = screenRepository.findAll();
			if (screens.size()>0) {
				return screens;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	

}

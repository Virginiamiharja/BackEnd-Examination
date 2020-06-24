package com.exambackend.virginiamiharja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exambackend.virginiamiharja.dao.CategoryRepo;
import com.exambackend.virginiamiharja.dao.MovieRepo;
import com.exambackend.virginiamiharja.entity.Category;
import com.exambackend.virginiamiharja.entity.Movie;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	 
//	CREATE
	@PostMapping("/add")
	public Movie addMovie (@RequestBody Movie movie) {
		movie.setId(0);
		return movieRepo.save(movie);
	}
	
//	READ
	@GetMapping
	public Iterable<Movie> readMovies(){
		return movieRepo.findAll();
	}
	
//	UPDATE 1st attempt 2nd attempt
	@PutMapping("/{movieId}")
	public Movie updateMovie(@PathVariable int movieId, @RequestBody Movie movie) {
		Movie findMovie = movieRepo.findById(movieId).get();
		movie.setId(movieId);
//		Biar categoriesnya ga hilang ketika movie di update
		movie.setCategories(findMovie.getCategories());
		return movieRepo.save(movie);
	}
	
//	DELETE 1st attempt 2nd attempt
	@DeleteMapping("/{movieId}")
	public String deleteMovie(@PathVariable int movieId) {
		Movie findMovie = movieRepo.findById(movieId).get();
				
//		Kosongin list categories karena list categories di entity movies gapake json ignore
		findMovie.setCategories(null);
//		findMovie.getCategories().clear();
	
//		Bikin error
//		findMovie.getCategories().forEach(category -> {
//			category.getMovies().remove(findMovie);
//			categoryRepo.save(category);
//		});
		
		movieRepo.deleteById(findMovie.getId());
		return "Delete Success";
	}
	
//	Add category to a movie
	@PostMapping("/{movieId}/addcategory/{categoryId}")
	private Movie addMovieCategory(@PathVariable int movieId, @PathVariable int categoryId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findMovie.getCategories().add(findCategory);
		return movieRepo.save(findMovie);
	}
	
//	Remove category from a movie
	@DeleteMapping("{movieId}/removecategory/{categoryId}")
	private Movie removeMovieCategory(@PathVariable int movieId, @PathVariable int categoryId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		findMovie.getCategories().remove(findCategory);
		return movieRepo.save(findMovie);
	}
	
}

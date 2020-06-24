package com.exambackend.virginiamiharja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exambackend.virginiamiharja.dao.CategoryRepo;
import com.exambackend.virginiamiharja.dao.MovieRepo;
import com.exambackend.virginiamiharja.entity.Category;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private MovieRepo movieRepo;
	
//	CREATE
	@PostMapping("/add")
	public Category addCategory(@RequestBody Category category) {
		category.setId(0);
		return categoryRepo.save(category);
	}
	
//	READ
	@GetMapping
	public Iterable<Category> readCategories() {
		return categoryRepo.findAll();
	}
	
//	UPDATE 1st attempt 2nd attempt
	@PutMapping("/{categoryId}")
	public Category updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		category.setId(categoryId);
//		Biar ketika diupdate, movie yang connect sama dia ga hilang
		category.setMovies(findCategory.getMovies());
		return categoryRepo.save(category);
	}
	
//	DELETE 1st attempt 2nd attempt
	@DeleteMapping("/{categoryId}")
	public String deleteCategory(@PathVariable int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();

//		Bikin error
//		findCategory.setMovies(null);
		
		findCategory.getMovies().forEach(movie -> {
			movie.getCategories().remove(findCategory);
			movieRepo.save(movie);
		});

		categoryRepo.deleteById(categoryId);
		return "Delete success";
	}

}

package com.exambackend.virginiamiharja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.exambackend.virginiamiharja.entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
	
}

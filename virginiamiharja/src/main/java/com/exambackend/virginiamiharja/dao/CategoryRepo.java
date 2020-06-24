package com.exambackend.virginiamiharja.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exambackend.virginiamiharja.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}

package com.vigneshsuryah.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigneshsuryah.springboot.entity.Project;

@Repository
public interface ProjectManagerRepository extends JpaRepository<Project,Long>{

}


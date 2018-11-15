package com.vigneshsuryah.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigneshsuryah.springboot.entity.Task;

@Repository
public interface TaskManagerRepository extends JpaRepository<Task,Long>{

}


package com.example.cch.psqlredisdemo.repository;

import com.example.cch.psqlredisdemo.model.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM Questions q WHERE q.title LIKE CONCAT('%',:title,'%')", nativeQuery = true)
    Page<Question> findQuestionByTitle(@Param("title") String title, Pageable pageable);
}

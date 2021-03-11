package com.example.cch.psqlredisdemo.repository;

import java.util.List;

import com.example.cch.psqlredisdemo.model.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}

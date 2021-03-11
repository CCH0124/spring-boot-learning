package com.example.cch.psqlredisdemo.controller;

import com.example.cch.psqlredisdemo.exception.ResourceNotFoundException;
import com.example.cch.psqlredisdemo.model.Answer;
import com.example.cch.psqlredisdemo.repository.AnswerRepository;
import com.example.cch.psqlredisdemo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(path = "/questions/{questionId}/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "answers",key = "#questionId")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return this.answerRepository.findByQuestionId(questionId);
    }

    @PostMapping(path = "/questions/{questionId}/answers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer) {
        return this.questionRepository.findById(questionId).map(question -> {
            answer.setQuestion(question);
            return answerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @PutMapping(path = "/questions/{questionId}/answers/{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(value = "answers",key = "#answerId")
    public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId,
            @Valid @RequestBody Answer answerRequest) {
        if (!this.questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }

        return this.answerRepository.findById(answerId).map(answer -> {
            answer.setText(answerRequest.getText());
            return answerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

    @DeleteMapping(path = "/questions/{questionId}/answers/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "answers", allEntries = true)
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        if (!this.questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }

        return this.answerRepository.findById(answerId).map(answer -> {
            this.answerRepository.delete(answer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));

    }
}
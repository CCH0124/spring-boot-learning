package com.example.cch.psqlredisdemo.controller;

import javax.validation.Valid;

import com.example.cch.psqlredisdemo.exception.ResourceNotFoundException;
import com.example.cch.psqlredisdemo.model.Question;
import com.example.cch.psqlredisdemo.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(path = "/questions/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Question> getQuestions(Pageable pageable) {
        return this.questionRepository.findAll(pageable);
    }

    @GetMapping(path = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Question> getQuestionsByTitle(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        if (title == null) {
            // return this.questionRepository.findAll(paging);
            return getQuestions(paging);
        } else {
            return this.questionRepository.findQuestionByTitle(title, paging);
        }

    }

    @PostMapping(path = "/questions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Question createQuestion(@Valid @RequestBody Question question) {
        return this.questionRepository.save(question);
    }

    @PutMapping(path = "/questions/{questionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CachePut(value = "questions",key = "#questionId")
    public Question updateQuestion(@PathVariable(value = "questionId") Long questionId,
            @Valid @RequestBody Question questionRequest) {
        return this.questionRepository.findById(questionId).map(q -> {
            q.setTitle(questionRequest.getTitle());
            q.setDescription(questionRequest.getDescription());
            return this.questionRepository.save(q);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @DeleteMapping(path = "/questions/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "questions", allEntries = true)
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "questionId") Long questionId) {
        return this.questionRepository.findById(questionId).map(q -> {
            this.questionRepository.delete(q);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}

package com.example.quizapp;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("topic/{topic}")
    public List<Question> getQuestionByTopic(@PathVariable String topic) {
        return questionService.getQuestionByTopic(topic);
    }

    @PostMapping("addQuestion")
    public void addQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
    }

}

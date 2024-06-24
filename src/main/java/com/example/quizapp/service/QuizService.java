package com.example.quizapp.service;

import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String topic, int noOfQuestions, String quizTitle) {
//        try {

            List<Question> questions = questionDao.findRandomQuestionsByCategory(topic, noOfQuestions);

            Quiz quiz = new Quiz();
            quiz.setTitle(quizTitle);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error ", HttpStatus.BAD_REQUEST);
//        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questionsFromDb = quiz.get().getQuestions();

        List<QuestionWrapper> questions = new ArrayList<>();

        for(Question q: questionsFromDb) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questions.add(qw);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questionsFromDb = quiz.get().getQuestions();

        Integer correctResponses = 0;
        int noOfQuestions = quiz.get().getQuestions().size();

        for(int i = 0;i<noOfQuestions;i++) {
            if(Objects.equals(questionsFromDb.get(i).getRightAnswer(), responses.get(i).getResponse())) correctResponses++;
        }

        return new ResponseEntity<>(correctResponses, HttpStatus.OK);
    }
}

package com.akat.quiz.services.impl;

import com.akat.quiz.dao.impl.AnswerRepo;
import com.akat.quiz.dao.impl.QuestionRepo;
import com.akat.quiz.dao.impl.QuizRepo;
import com.akat.quiz.model.Answer;
import com.akat.quiz.model.Question;
import com.akat.quiz.model.Quiz;
import com.akat.quiz.services.interfaces.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("MainService")
public class MainService implements IMainService {

    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;


    @Autowired
    public MainService(QuizRepo quizRepo, QuestionRepo questionRepo, AnswerRepo answerRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    @Override
    public void test(){
        Quiz quiz = quizRepo.save(new Quiz());


        Question q1= questionRepo.save(Question.builder().quiz(quiz).build());
        Question q2= questionRepo.save(Question.builder().quiz(quiz).build());

        Answer a1 = answerRepo.save(Answer.builder().answer("First").question(q1).build());
        Answer a2 = answerRepo.save(Answer.builder().answer("Second").question(q1).build());
        Answer a3 = answerRepo.save(Answer.builder().answer("Third").question(q2).build());
        Answer a4 = answerRepo.save(Answer.builder().answer("Fourth").question(q2).build());

        q1.setAnswers(Arrays.asList(a1, a2));
        q2.setAnswers(Arrays.asList(a3, a4));

        quiz.setQuestions(Arrays.asList(q1, q2));
    }

    @Override
    public String whatTimeIsIt() {
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        quizRepo.saveAndFlush(new Quiz());

        quiz.getQuestions().forEach((question) ->{ // for each question in the quiz we must do two operations:
            questionRepo.save(question); //firstly save the question for getting ID
            System.out.println(question);
            question.getAnswers().forEach((answer) -> {
                answer.setQuestion(question); //here we assign the question for  the answer
                answerRepo.save(answer);
            });

        });
        System.out.println(quiz);
        return quiz;
    }


//    @Override
//    public Answer save(Answer a) {
//        return answerRepo.save(a);
//    }

}

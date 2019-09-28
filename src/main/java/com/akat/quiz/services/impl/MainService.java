package com.akat.quiz.services.impl;

import com.akat.quiz.dao.impl.AnswerRepo;
import com.akat.quiz.dao.impl.QuestionRepo;
import com.akat.quiz.dao.impl.QuizRepo;
import com.akat.quiz.model.Answer;
import com.akat.quiz.model.Question;
import com.akat.quiz.model.Quiz;
import com.akat.quiz.services.interfaces.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("MainService")
@Profile({"production", "test"})
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
    public Quiz test(){
        Quiz quiz = quizRepo.save(new Quiz());
        Question q1 = new Question();
        Question q2 = new Question();
        q1.setQuiz(quiz);
        q2.setQuiz(quiz);
        questionRepo.save(q1);
        questionRepo.save(q2);

        Answer a1 = new Answer();
        Answer a2 = new Answer();
        Answer a3 = new Answer();
        Answer a4 = new Answer();
        a1.setAnswer("First");
        a2.setAnswer("Second");
        a3.setAnswer("Third");
        a4.setAnswer("Fourth");
        a1.setQuestion(q1);
        a2.setQuestion(q1);
        a3.setQuestion(q2);
        a4.setQuestion(q2);
        answerRepo.save(a1);
        answerRepo.save(a2);
        answerRepo.save(a3);
        answerRepo.save(a4);

        q1.setAnswers(Arrays.asList(a1, a2));
        q2.setAnswers(Arrays.asList(a3, a4));

        quiz.setQuestions(Arrays.asList(q1, q2));

        return quiz;
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

    @Override
    public void deleteQuizById(Long id) {
        quizRepo.deleteById(id);
    }

    @Override
    public Quiz updateQuiz(Long id, Quiz quiz) {
        Quiz q = quizRepo.getOne(id);
        q.setQuestions(quiz.getQuestions());
        return q;
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepo.getOne(id);
    }

}

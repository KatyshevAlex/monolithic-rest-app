package com.akat.quiz.services.impl;

import com.akat.quiz.dao.impl.quiz.AnswerRepo;
import com.akat.quiz.dao.impl.quiz.QuestionRepo;
import com.akat.quiz.dao.impl.quiz.QuizRepo;
import com.akat.quiz.model.entities.Answer;
import com.akat.quiz.model.entities.Question;
import com.akat.quiz.model.entities.Quiz;
import com.akat.quiz.services.interfaces.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service("MainService")
@Profile({"production", "test"})
@Transactional
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
        q1.setRightAnswerId(3);
        q1.setQuestion("AAAAAAAAAAAAAA");
        q2.setQuiz(quiz);
        q2.setRightAnswerId(2);
        q2.setQuestion("BBBBBBBBBBBBBBB");
        questionRepo.save(q1);
        questionRepo.save(q2);

        Answer a1 = new Answer();
        Answer a2 = new Answer();
        Answer a3 = new Answer();
        Answer a4 = new Answer();
        a1.setText("First");
        a2.setText("Second");
        a3.setText("Third");
        a4.setText("Fourth");
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
        Quiz quizWithId = quizRepo.save(quiz);
        quiz.getQuestions().stream().peek((question) ->{ // for each question in the quiz we must do two operations:
            question.setQuiz(quizWithId); // assign Quiz by id
            questionRepo.saveAndFlush(question); //save the question for getting ID
            question.getAnswers().stream().peek((answer) -> {
                answer.setQuestion(question); //here we assign the question for  the answer
                answerRepo.saveAndFlush(answer);
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

package com.akat.quiz.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "quizzes", schema = "quiz")
@SequenceGenerator(name = "sq_quiz", sequenceName = "sq_quiz", allocationSize = 1, schema = "quiz")
@Data
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_quiz")
    Long id;


    @OneToMany(mappedBy="quiz", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Question> questions;
}

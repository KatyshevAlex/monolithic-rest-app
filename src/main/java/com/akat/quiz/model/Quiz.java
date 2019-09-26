package com.akat.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "quizes", schema = "quiz")
@SequenceGenerator(name = "sq_quiz", sequenceName = "sq_quiz", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_quiz")
    Long id;


    @OneToMany(mappedBy="quiz", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Question> questions;
}

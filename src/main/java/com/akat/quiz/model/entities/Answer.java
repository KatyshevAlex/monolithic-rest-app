package com.akat.quiz.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "answers", schema = "quiz")
@SequenceGenerator(name = "sq_answers", sequenceName = "sq_answers", allocationSize = 1, schema = "quiz")
public class Answer {

    @Column(name = "answer")
    String text;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_answers")
    @Column(name = "id")
    Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    Question question;
}


package com.akat.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "answers", schema = "quiz")
@SequenceGenerator(name = "sq_answer", sequenceName = "sq_answer", allocationSize = 1)
@Builder
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_answer")
    @Column(name = "id")
    Long id;

    @Column(name = "answer")
    String answer;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    Question question;
}


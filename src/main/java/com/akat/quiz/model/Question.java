package com.akat.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "questions", schema = "quiz")
@Builder
@SequenceGenerator(name = "sq_question", sequenceName = "sq_question", allocationSize = 1)
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_question")
    @Column(name = "id")
    Long id;

    @OneToMany(mappedBy="question", fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Answer> answers;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="quiz_id")
    Quiz quiz;
}

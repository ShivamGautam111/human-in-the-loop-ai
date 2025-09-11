package com.frontdesk.test.human_in_the_loop.model;

import jakarta.persistence.*;

@Entity
public class KnowledgeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String question;

    @Column(nullable = false, length = 2048)
    private String answer;

    public KnowledgeBase() {}

    public KnowledgeBase(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}

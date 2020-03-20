package com.s720d.whosdatpokemon;

import java.util.List;

public class Question {

    private String id;

    private String name;

    private List<String> possible_answer;

    public Question(String id, String name, List<String> possible_answer) {
        this.id = id;
        this.name = name;
        this.possible_answer = possible_answer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPossible_answer() {
        return possible_answer;
    }
}

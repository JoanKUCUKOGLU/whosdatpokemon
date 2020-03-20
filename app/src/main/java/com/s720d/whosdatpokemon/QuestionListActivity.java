package com.s720d.whosdatpokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {

    private List<Question> questionList = new ArrayList<>();
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        questionList.addAll(getListQuestions("easy"));
        questionList.addAll(getListQuestions("medium"));
        questionList.addAll(getListQuestions("hard"));

        adapter = new QuestionAdapter(questionList);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Question> getListQuestions(String difficulty) {
        List<Question> listQuestions = new ArrayList<>();
        JSONArray pokemonJson = new JSONArray();

        try {
            InputStream is = getResources().openRawResource(R.raw.pokemon_data);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            pokemonJson = new JSONObject(new String(buffer, "UTF-8")).getJSONArray(difficulty);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++) {
            try {
                JSONObject qObject = pokemonJson.getJSONObject(i);
                List<String> possibleAnswer = new ArrayList<>();

                for (int j = 0; j < 4; j++) {
                    possibleAnswer.add((String) qObject.getJSONArray("possible_answers").get(j));
                }

                Question question = new Question(
                        qObject.getString("id"),
                        qObject.getString("name"),
                        possibleAnswer);
                listQuestions.add(question);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listQuestions;
    }
}

package com.s720d.whosdatpokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ImageView spriteImage;

    private Button answer1Button;

    private Button answer2Button;

    private Button answer3Button;

    private Button answer4Button;

    private Button nextQuestionButton;

    private int currentIndex = 0;
    private Question currentQuestion;
    private ColorFilter currentColorFilter;

    private int nbCorrectAnswers = 0;

    private List<Question> listQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        spriteImage = findViewById(R.id.spriteImageView);
        answer1Button = findViewById(R.id.answerOneButton);
        answer2Button = findViewById(R.id.answerTwoButton);
        answer3Button = findViewById(R.id.answerThreeButton);
        answer4Button = findViewById(R.id.answerFourButton);
        nextQuestionButton = findViewById(R.id.nextQuestionButton);
        nextQuestionButton.setVisibility(View.INVISIBLE);

        // Get all Questions by difficulty received by HomeActivity (through RulesActivity)
        listQuestions = getListQuestions();

        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newResponse(answer1Button);
            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newResponse(answer2Button);
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newResponse(answer3Button);
            }
        });
        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newResponse(answer4Button);
            }
        });
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestion();
            }
        });

        newGame();
    }

    public void newGame() {
        Collections.shuffle(listQuestions);
        currentIndex = 0;
        nbCorrectAnswers = 0;

        newQuestion();
    }

    public void newQuestion() {
        answer1Button.setBackgroundColor(Color.parseColor("#b2b2b2"));
        answer2Button.setBackgroundColor(Color.parseColor("#b2b2b2"));
        answer3Button.setBackgroundColor(Color.parseColor("#b2b2b2"));
        answer4Button.setBackgroundColor(Color.parseColor("#b2b2b2"));
        nextQuestionButton.setVisibility(View.INVISIBLE);

        currentQuestion = listQuestions.get(currentIndex);

        spriteImage.setImageResource(getResources().getIdentifier("sprite_" + currentQuestion.getId(), "drawable", getPackageName()));
        currentColorFilter = spriteImage.getColorFilter();
        spriteImage.setColorFilter(Color.parseColor("#000000"));

        Integer[] answerList = {0, 1, 2, 3};
        Collections.shuffle(Arrays.asList(answerList));

        answer1Button.setText(currentQuestion.getPossible_answer().get(answerList[0]));
        answer2Button.setText(currentQuestion.getPossible_answer().get(answerList[1]));
        answer3Button.setText(currentQuestion.getPossible_answer().get(answerList[2]));
        answer4Button.setText(currentQuestion.getPossible_answer().get(answerList[3]));
        changeButtonEnabled(true);
    }

    public void newResponse(Button currentButton) {
        changeButtonEnabled(false);
        spriteImage.setColorFilter(currentColorFilter);
        if (currentButton.getText().equals(currentQuestion.getName())) {
            nbCorrectAnswers += 1;
        } else {
            currentButton.setBackgroundColor(Color.parseColor("#ff1919"));
        }

        highlightGoodAnswer();
        currentIndex += 1;
        if (currentIndex < 10) {
            nextQuestionButton.setVisibility(View.VISIBLE);
        }
    }

    public void highlightGoodAnswer() {
        if (answer1Button.getText().equals(currentQuestion.getName())) {
            answer1Button.setBackgroundColor(Color.parseColor("#4ca64c"));
        }
        if (answer2Button.getText().equals(currentQuestion.getName())) {
            answer2Button.setBackgroundColor(Color.parseColor("#4ca64c"));
        }
        if (answer3Button.getText().equals(currentQuestion.getName())) {
            answer3Button.setBackgroundColor(Color.parseColor("#4ca64c"));
        }
        if (answer4Button.getText().equals(currentQuestion.getName())) {
            answer4Button.setBackgroundColor(Color.parseColor("#4ca64c"));
        }
    }

    public void changeButtonEnabled(Boolean state) {
        answer1Button.setEnabled(state);
        answer2Button.setEnabled(state);
        answer3Button.setEnabled(state);
        answer4Button.setEnabled(state);
    }

    public List<Question> getListQuestions() {
        List<Question> listQuestions = new ArrayList<>();
        JSONArray pokemonJson = new JSONArray();
        String difficulty = getIntent().getStringExtra("difficulty");

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

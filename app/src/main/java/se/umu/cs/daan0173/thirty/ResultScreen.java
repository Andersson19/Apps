package se.umu.cs.daan0173.thirty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultScreen extends AppCompatActivity {

    //Display total points
    private TextView totalPoints;
    //Button for returning to main menu
    private Button mPlayAgainButton;

    //Display each rounds choice and points in table 
    private ArrayList<String> roundChoices;
    private ArrayList<Integer> roundPoints;

    private int[] roundPointsIDs = new int[] {R.id.points1, R.id.points2, R.id.points3,
            R.id.points4, R.id.points5, R.id.points6, R.id.points7,
            R.id.points8, R.id.points9, R.id.points10, };

    private int[] roundChoiceIDs = new int[] {R.id.choice1, R.id.choice2, R.id.choice3,
            R.id.choice4, R.id.choice5, R.id.choice6, R.id.choice7,
            R.id.choice8, R.id.choice9, R.id.choice10, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setupResultData();

        fillResultTable();

        setTotalPoints();

        setupPlayAgainBtn();
    }

    public void setupPlayAgainBtn() {
        mPlayAgainButton = findViewById(R.id.playAgainBtn);
        mPlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultScreen.this, MainActivity.class));
            }
        });
    }

    public void setupResultData() {
        //Get the variables that were passed from GameScreen Activity
        //The two variables are two ArrayLists that containing the choice and points generated that round
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roundChoices = extras.getStringArrayList("roundChoices");
            roundPoints = extras.getIntegerArrayList("roundPoints");
        }
    }

    public void fillResultTable() {
        // Fill table in activity_result.xml with choice and result for each round
        for (int i = 0; i < roundChoiceIDs.length; i++) {
            TextView choiceText = findViewById(roundChoiceIDs[i]);
            TextView pointsText = findViewById(roundPointsIDs[i]);
            choiceText.setText(roundChoices.get(i));
            pointsText.setText("" + roundPoints.get(i));
        }
    }

    public void setTotalPoints() {
        totalPoints = findViewById(R.id.total_points);

        int sum = 0;
        for (int i = 0; i < roundPoints.size(); i++) {
            sum += roundPoints.get(i);
        }
        totalPoints.setText("Total Points: " + sum);
    }

}

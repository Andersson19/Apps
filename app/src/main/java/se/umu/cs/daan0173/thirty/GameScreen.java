package se.umu.cs.daan0173.thirty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    private Button mCalc_button;
    private Button mThrow_button;
    private Random rand = new Random();

    //Algorithm used for calculating score for given choice each round
    private Algorithm algo;

    //Display points
    private TextView mPointView;

    //ImageButton references for unkept dice
    private ImageButton[] dice;

    //ImageButton references for kept dice
    private ImageButton[] keepDice;

    //List for keeping track for each die if they are kept or not
    private ArrayList<Boolean> keepDie;

    //storing results of dices thrown
    private int[] faces;

    //storing calculated points
    private int points;

    //Keep track of throws each round and how many rounds have been played
    private int throwCounter;
    private int roundCounter;

    //List for storing choices
    private List<String> choicesList;

    //Reference to the Spinner containing choices
    private Spinner dropdown;
    private ArrayAdapter<String> dropdownArrayAdapter;

    //Used for keeping track of what choice was made and how many points generated for specific round.
    //Gets passed onto ResultScreen Activity
    protected ArrayList<String> roundChoices = new ArrayList<>();
    protected ArrayList<Integer> roundPoints = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keepDie = new ArrayList<>();

        //init to 0
        throwCounter = 0;
        roundCounter = 0;

        faces = new int[6];

        //No dice are kept at startup
        for (int i = 0; i < 6; i++) {
            keepDie.add(false);
        }

        dice = new ImageButton[] { findViewById(R.id.dice1),
                findViewById(R.id.dice2),
                findViewById(R.id.dice3),
                findViewById(R.id.dice4),
                findViewById(R.id.dice5),
                findViewById(R.id.dice6)};

        keepDice = new ImageButton[] { findViewById(R.id.keepDie1),
                findViewById(R.id.keepDie2),
                findViewById(R.id.keepDie3),
                findViewById(R.id.keepDie4),
                findViewById(R.id.keepDie5),
                findViewById(R.id.keepDie6)};

        //set onClickListeners for each die in unkept area
        for (int i = 0; i < dice.length; i++) {
            ImageButton d = dice[i];
            final int j = i;
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Clicking unkept die hides that die and shows kept die instead
                    dice[j].setVisibility(View.INVISIBLE);
                    keepDice[j].setVisibility(View.VISIBLE);

                    //keep track of kept die
                    keepDie.set(j, true);
                }
            });
        }

        //set onClickListeners for each die in kept area
        for (int i = 0; i < dice.length; i++) {
            ImageButton kd = keepDice[i];
            final int j = i;
            kd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Clicking kept die hides that die and shows unkept die instead
                    dice[j].setVisibility(View.VISIBLE);
                    keepDice[j].setVisibility(View.INVISIBLE);

                    //keep track of unkept die
                    keepDie.set(j, false);
                }
            });
        }

        mThrow_button = findViewById(R.id.throwButton);
        mThrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Only three throws allowed per round
                if (throwCounter < 3) {
                    throw_dice();
                } else {
                    Toast.makeText(getApplicationContext(), "Only 3 throws allowed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //onClickListener to calculate score for that round
        mCalc_button = findViewById(R.id.calc_button);
        mCalc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roundCounter < 10) {
                    //calculate result for given choice and results of dice
                    calc_result();

                    //increment rounds
                    roundCounter++;
                    //reset throws allowed for new round
                    throwCounter = 0;

                    //reset so all dice are unkept for start of new round
                    resetDice();

                    //throw dice at start of new round
                    throw_dice();

                    if (roundCounter == 10) {
                        mCalc_button.setText("Show Results");
                    }
                } else {

                    //All rounds played, display ResultScreen
                    Intent i = new Intent(GameScreen.this, ResultScreen.class);
                    //Pass on choices made each round and what points they each generated
                    i.putExtra("roundChoices", roundChoices);
                    i.putExtra("roundPoints", roundPoints);
                    startActivity(i);
                }
            }
        });

        dropdown = findViewById(R.id.choice);

        //List of all choices
        choicesList = new ArrayList<>();
        choicesList.add("Low");
        choicesList.add("4");
        choicesList.add("5");
        choicesList.add("6");
        choicesList.add("7");
        choicesList.add("8");
        choicesList.add("9");
        choicesList.add("10");
        choicesList.add("11");
        choicesList.add("12");

        //Found solution of using ArrayAdapters to remove elements from Spinner, online at: https://android--code.blogspot.com/2015/08/android-spinner-remove-item.html
        dropdownArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, choicesList);
        dropdownArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        dropdown.setAdapter(dropdownArrayAdapter);

        mPointView = findViewById(R.id.pointView2);


        //throw dice first time
        throw_dice();
    }

    //Reset all kept dice to unkept stage
    public void resetDice() {
        for (int i = 0; i < keepDie.size(); i++) {
            dice[i].setVisibility(View.VISIBLE);
            keepDice[i].setVisibility(View.INVISIBLE);
            keepDie.set(i, false);
        }
    }

    //Throws all unkept dice
    public void throw_dice() {
        for (int i = 0; i < 6; i++) {

            //check for kept die or not
            if (!keepDie.get(i)) {

                //get outcome of dice throw
                int rand_int = rand.nextInt(6) + 1;

                //change view for thrown dice based on outcome
                switch (rand_int) {
                    case 1:
                        dice[i].setImageResource(R.drawable.white1);
                        keepDice[i].setImageResource(R.drawable.white1);
                        break;
                    case 2:
                        dice[i].setImageResource(R.drawable.white2);
                        keepDice[i].setImageResource(R.drawable.white2);
                        break;
                    case 3:
                        dice[i].setImageResource(R.drawable.white3);
                        keepDice[i].setImageResource(R.drawable.white3);
                        break;
                    case 4:
                        dice[i].setImageResource(R.drawable.white4);
                        keepDice[i].setImageResource(R.drawable.white4);
                        break;
                    case 5:
                        dice[i].setImageResource(R.drawable.white5);
                        keepDice[i].setImageResource(R.drawable.white5);
                        break;
                    case 6:
                        dice[i].setImageResource(R.drawable.white6);
                        keepDice[i].setImageResource(R.drawable.white6);
                        break;
                }

                //save outcome of dice throw
                faces[i] = rand_int;

            }

        }

        //increment throws
        throwCounter++;
    }

    //Calculates points for given choice
    //Uses Algorithm.java for all calculations except for "Low"
    public void calc_result() {
        String choice = (String) dropdown.getSelectedItem();
        switch (choice) {
            case "Low":
                int sum = 0;
                for (int eyes : faces) {
                    if (eyes <= 3) {
                        sum += eyes;
                    }

                }
                roundChoices.add(roundCounter, "Low");
                roundPoints.add(roundCounter, sum);

                points += sum;
                mPointView.setText("" + points);
                choicesList.remove("Low");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "4":
                algo = new Algorithm(faces, 4);

                roundChoices.add(roundCounter, "4");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("4");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "5":
                algo = new Algorithm(faces, 5);

                roundChoices.add(roundCounter, "5");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("5");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "6":
                algo = new Algorithm(faces, 6);

                roundChoices.add(roundCounter, "6");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("6");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "7":
                algo = new Algorithm(faces, 7);

                roundChoices.add(roundCounter, "7");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("7");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "8":
                algo = new Algorithm(faces, 8);

                roundChoices.add(roundCounter, "8");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("8");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "9":
                algo = new Algorithm(faces, 9);

                roundChoices.add(roundCounter, "9");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("9");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "10":
                algo = new Algorithm(faces, 10);

                roundChoices.add(roundCounter, "10");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("10");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "11":
                algo = new Algorithm(faces, 11);

                roundChoices.add(roundCounter, "11");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("11");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "12":
                algo = new Algorithm(faces, 12);

                roundChoices.add(roundCounter, "12");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("12");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
        }
    }
}
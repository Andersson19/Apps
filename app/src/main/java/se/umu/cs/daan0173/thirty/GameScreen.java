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
    private ImageButton[] dice;
    private ImageButton[] keepDice;
    private ArrayList<Boolean> keepDie;
    private int throwCounter;

    private int[] faces;
    private Random rand = new Random();
    private Spinner dropdown;
    private TextView mPointView;
    private List<String> choicesList;
    private ArrayAdapter<String> dropdownArrayAdapter;
    private int points;
    private Algorithm algo;
    private int roundCounter;

    protected ArrayList<String> roundChoices = new ArrayList<>();
    protected ArrayList<Integer> roundPoints = new ArrayList<Integer>();

    public ArrayList<String> getRoundChoices() {
        return roundChoices;
    }

    public ArrayList<Integer> getRoundPoints() {
        return roundPoints;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keepDie = new ArrayList<>();
        throwCounter = 0;
        faces = new int[6];

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

        for (int i = 0; i < dice.length; i++) {
            ImageButton d = dice[i];
            final int j = i;
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keepDie.set(j, true);
                    dice[j].setVisibility(View.INVISIBLE);
                    keepDice[j].setVisibility(View.VISIBLE);
                }
            });
        }

        for (int i = 0; i < dice.length; i++) {
            ImageButton kd = keepDice[i];
            final int j = i;
            kd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keepDie.set(j, false);
                    dice[j].setVisibility(View.VISIBLE);
                    keepDice[j].setVisibility(View.INVISIBLE);

                }
            });
        }

        mThrow_button = findViewById(R.id.throwButton);
        mThrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (throwCounter < 3) {
                    throw_dice();
                    throwCounter++;
                } else {
                    Toast.makeText(getApplicationContext(), "Only 3 throws allowed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCalc_button = findViewById(R.id.calc_button);
        mCalc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roundCounter < 10) {
                    calc_result();
                    throw_dice();
                    roundCounter++;
                    if (roundCounter == 10) {
                        mCalc_button.setText("Show Results");
                    }
                } else {

                    Intent i = new Intent(GameScreen.this, ResultScreen.class);
                    i.putExtra("roundChoices", roundChoices);
                    i.putExtra("roundPoints", roundPoints);
                    startActivity(i);

                    //TODO: move to result screen§
                }
            }
        });

        dropdown = findViewById(R.id.choice);

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

        dropdownArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, choicesList);

        dropdownArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        dropdown.setAdapter(dropdownArrayAdapter);

        mPointView = findViewById(R.id.pointView2);
        roundCounter = 0;

        //throw dice first time
        throw_dice();
        throwCounter++;
    }

    public void throw_dice() {
        for (int i = 0; i < 6; i++) {

            if (!keepDie.get(i)) {

                //get outcome of dice throw
                int rand_int = rand.nextInt(6) + 1;

                //change view for thrown dice
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

                //save result of dice throw
                faces[i] = rand_int;

            }

        }
    }

    public void calc_result() {
        String choice = (String) dropdown.getSelectedItem();
        switch (choice) {
            case "Low":
                int sum = 0;
                System.out.println("Searching for combinations of : " + choice);
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
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 4);

                roundChoices.add(roundCounter, "4");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("4");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "5":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 5);

                roundChoices.add(roundCounter, "5");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("5");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "6":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 6);

                roundChoices.add(roundCounter, "6");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("6");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "7":

                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 7);

                roundChoices.add(roundCounter, "7");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("7");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "8":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 8);

                roundChoices.add(roundCounter, "8");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("8");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "9":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 9);

                roundChoices.add(roundCounter, "9");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("9");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "10":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 10);

                roundChoices.add(roundCounter, "10");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("10");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "11":
                System.out.println("Searching for combinations of : " + choice);
                algo = new Algorithm(faces, 11);

                roundChoices.add(roundCounter, "11");
                roundPoints.add(roundCounter, algo.getResult());

                points += algo.getResult();
                mPointView.setText("" + points);
                choicesList.remove("11");
                dropdownArrayAdapter.notifyDataSetChanged();
                break;
            case "12":
                System.out.println("Searching for combinations of : " + choice);
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
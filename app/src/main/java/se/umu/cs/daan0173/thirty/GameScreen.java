package se.umu.cs.daan0173.thirty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    /*  ----------------
        ---Attributes---
        ----------------  */

    private static final String TAG = "GameScreen";

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
    private boolean[] keepDie;

    //storing results of dices thrown
    private int[] faces;

    //storing calculated points
    private int points;

    //Keep track of throws each round and how many rounds have been played
    private int throwCounter;
    private int roundCounter;

    //List for storing choices
    private ArrayList<String> choicesList;

    //Reference to the Spinner containing choices
    private Spinner dropdown;
    private ArrayAdapter<String> dropdownArrayAdapter;

    //Used for keeping track of what choice was made and how many points generated for specific round.
    //Gets passed onto ResultScreen Activity
    protected ArrayList<String> roundChoices = new ArrayList<>();
    protected ArrayList<Integer> roundPoints = new ArrayList<Integer>();

    /*  ---------------
        --- Methods ---
        ---------------   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "In the onCreate() event");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setupInit();
        setupDieImages();
        setupThrowBtn();
        setupCalcBtn();
        setupDropdown();

        //throw dice first time
        if (savedInstanceState == null) {
            throw_dice();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putIntArray("faces", faces);
        saveInstanceState.putBooleanArray("keepDie", keepDie);
        saveInstanceState.putInt("throwCounter", throwCounter);
        saveInstanceState.putInt("roundCounter", roundCounter);
        saveInstanceState.putStringArrayList("choicesList", choicesList);
        saveInstanceState.putInt("totalPoints", points);
        saveInstanceState.putStringArrayList("roundChoices", roundChoices);
        saveInstanceState.putIntegerArrayList("roundPoints", roundPoints);
        Log.d(TAG, "In onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "In onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        keepDie = savedInstanceState.getBooleanArray("keepDie");
        faces = savedInstanceState.getIntArray("faces");
        throwCounter = savedInstanceState.getInt("throwCounter");
        roundCounter = savedInstanceState.getInt("roundCounter");
        choicesList = savedInstanceState.getStringArrayList("choicesList");
        points = savedInstanceState.getInt("totalPoints");
        roundChoices = savedInstanceState.getStringArrayList("roundChoices");
        roundPoints = savedInstanceState.getIntegerArrayList("roundPoints");

        //Update View
        mPointView.setText(""+points);

        //HACK: this is for sure not best practice
        //But I could not find a way to update the Spinner using the adapter in any other way than
        //creating a fresh one with the restored choicesList
        //i.e this is done in OnCreate() as well, but this will update it
        dropdownArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, choicesList);
        dropdownArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        dropdown.setAdapter(dropdownArrayAdapter);

        updateDice();

    }

    public void setupThrowBtn() {
        mThrow_button = findViewById(R.id.throwButton);
        mThrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Only three throws allowed per round
                if (throwCounter < 3) {
                    throw_dice();
                    Log.d(TAG, "throwing dice");
                } else {
                    Log.d(TAG, "Showing Toast");
                    Toast.makeText(getBaseContext(), "Only 3 throws allowed!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setupCalcBtn() {
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
    }

    public void setupDieImages() {
        dice = new ImageButton[] { findViewById(R.id.dice1),
                findViewById(R.id.dice2),
                findViewById(R.id.dice3),
                findViewById(R.id.dice4),
                findViewById(R.id.dice5),
                findViewById(R.id.dice6)};

        //set onClickListeners for each die in unkept area
        for (int i = 0; i < dice.length; i++) {
            ImageButton d = dice[i];
            final int j = i;
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Clicking unkept die hides that die and shows kept die instead
                    if (!keepDie[j]) {
                        dice[j].setAlpha(0.5f);
                        keepDie[j] = true;
                    } else {
                        dice[j].setAlpha(1.0f);
                        keepDie[j] = false;
                    }

                    //keep track of kept die

                }
            });
        }
    }

    public void setupInit() {
        //init to 0
        throwCounter = 0;
        roundCounter = 0;

        //allocate arrays keeping track of dice result / status
        keepDie = new boolean[6];
        faces = new int[6];

        mPointView = findViewById(R.id.pointView2);
    }

    public void setupDropdown() {
        dropdown = findViewById(R.id.choice);

        String[] choices = new String[] {
                "Low",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12"
        };

        //List of all choices
        choicesList = new ArrayList<>(Arrays.asList(choices));

        //Found solution of using ArrayAdapters to remove elements from Spinner, online at: https://android--code.blogspot.com/2015/08/android-spinner-remove-item.html
        //only create it if one already doesn't exist
        dropdownArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, choicesList);
        dropdownArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        dropdown.setAdapter(dropdownArrayAdapter);
    }


    //update view for dice
    public void updateDice() {
        for (int i = 0; i < faces.length; i++) {
            switch(faces[i]) {
                case 1:
                    dice[i].setImageResource(R.drawable.white1);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
                case 2:
                    dice[i].setImageResource(R.drawable.white2);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
                case 3:
                    dice[i].setImageResource(R.drawable.white3);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
                case 4:
                    dice[i].setImageResource(R.drawable.white4);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
                case 5:
                    dice[i].setImageResource(R.drawable.white5);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
                case 6:
                    dice[i].setImageResource(R.drawable.white6);
                    if(keepDie[i]) {
                        dice[i].setAlpha(0.5f);
                    }
                    break;
            }
        }
    }

    //Throws all unkept dice
    public void throw_dice() {
        for (int i = 0; i < 6; i++) {

            //check for kept die or not
            if (!keepDie[i]) {

                //get outcome of dice throw
                int rand_int = rand.nextInt(6) + 1;

                //save outcome of dice throw
                faces[i] = rand_int;

            }
        }
        //update view
        updateDice();

        //increment throws
        throwCounter++;
    }

    public void calcByChoice(String choice) {
        algo = new Algorithm(faces, Integer.parseInt(choice));

        roundChoices.add(roundCounter, choice);
        roundPoints.add(roundCounter, algo.getResult());

        points += algo.getResult();
        mPointView.setText("" + points);
        choicesList.remove(choice);
        dropdownArrayAdapter.notifyDataSetChanged();
    }

    //Checking if String is numeric or not, found at: https://www.baeldung.com/java-check-string-number
    public boolean isNumeric(String choice) {
        if (choice == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(choice);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    //Calculates points for given choice
    //Uses Algorithm.java for all calculations except for "Low"
    public void calc_result() {
        String choice = (String) dropdown.getSelectedItem();

        if (choice.equals("Low")) {
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
        } else if (isNumeric(choice)) {
            calcByChoice(choice);
        } else {
            Log.d("GameScreen", "That choice is not valid");
        }
    }

    //Used for debugging
    public void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart() event");
    }

    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "In the onRestart() event");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume() event");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "In the onPause() event");
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop() event");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy() event");
    }

    //Reset all kept dice to unkept stage
    public void resetDice() {
        for (int i = 0; i < keepDie.length; i++) {
            dice[i].setAlpha(1.0f);
            keepDie[i] = false;
        }
    }
}
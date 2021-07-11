package se.umu.cs.daan0173.thirty;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button mStart_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().setTitle("Welcome to Thirty!");

        mStart_button = findViewById(R.id.start_button);
        mStart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start new Acitivity a.k.a Gamescreen
                startActivity(new Intent(MainActivity.this, GameScreen.class));
            }
        });
    }

}
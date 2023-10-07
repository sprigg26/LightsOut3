package com.example.lightsout3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    Button[][] buttons = new Button[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setText("Button");
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle button click here
                        Button clickedButton = (Button) v;
                        // You can perform actions based on which button was clicked
                        // For example, change the button's text or background color
                        clickedButton.setText("Clicked");
                    }
                });

                // Add the button to the GridLayout
                gridLayout.addView(buttons[i][j]);
            }
        }



    }

    //helper method to change the button colors
    private void toggleButtonColor(int row, int col)
    {
        Button button = buttons[row][col];
        if (button.getText().toString().equals(""))
        {
            button.setBackgroundColor(Color.BLACK);
            button.setText(" ");
        }
        else {
            button.setBackgroundColor(Color.WHITE);
            button.setText("");
        }
    }

}


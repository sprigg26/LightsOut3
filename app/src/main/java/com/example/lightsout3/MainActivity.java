package com.example.lightsout3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button[][] buttons = new Button[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        Button resetButton = findViewById(R.id.button2);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setBackgroundColor(getRandomColor());


                buttons[i][j].setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Button clickedButton = (Button) v;
                        int clickedRow = -1;
                        int clickedCol = -1;
                        for (int row = 0; row < 5; row++) {
                            for (int col = 0; col < 5; col++) {
                                if (buttons[row][col] == clickedButton) {
                                    clickedRow = row;
                                    clickedCol = col;
                                    break;
                                }
                            }
                        }

                        // Toggle the color of the clicked button
                        toggleButtonColor(clickedRow, clickedCol);

                        // Toggle the colors of adjacent buttons (up, down, left, and right)
                        toggleAdjacentButtons(clickedRow, clickedCol);

                        // Check if the player has won (all buttons are black)
                        if (isGameWon()) {
                            gridLayout.setBackgroundColor(Color.GREEN);
                        }
                    }

                });

                //add the buttons to the gridLayout
                gridLayout.addView(buttons[i][j]);

                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the reset button click event here
                        resetGrid(v); // Call your resetGrid method or any other logic
                        gridLayout.setBackgroundColor(Color.WHITE);

                    }
                });
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

    // Helper method to toggle the colors of adjacent buttons
    private void toggleAdjacentButtons(int row, int col) {
        //acquired help from online resources for this method (stackoverflow and forums)
        int[][] directions = { {0, -1}, {-1, 0}, {0, 1}, {1, 0} }; // Up, Left, Down, Right

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidPosition(newRow, newCol))
            {
                toggleButtonColor(newRow, newCol);
            }
        }
    }

    //helper method to prevent crashing by forcing the positions to be within the 5x5 grid
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 5 && col >= 0 && col < 5;
    }

    // Helper method to check if the player has won (all buttons are black)
    private boolean isGameWon() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[row][col].getBackground();
                if (buttonColor.getColor() != Color.BLACK)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private int getRandomColor() {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if(randomNumber == 1)
        {
            return Color.BLACK;
        }
        else
        {
            return Color.WHITE;
        }
    }


    public void resetGrid(View v) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col].setBackgroundColor(getRandomColor());
            }
        }

    }

}


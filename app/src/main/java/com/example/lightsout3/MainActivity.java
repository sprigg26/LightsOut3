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

    //creates a 2d array to store all 25 buttons and allow for interactivity between them (toggle methods)
    Button[][] buttons = new Button[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //calls gridLayout from xml but I needed to add libraries to support
        // it bc gridLayout is a legacy feature
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        //reset button to randomize the puzzle
        Button resetButton = findViewById(R.id.button2);

        //nested for loop that creates 25 buttons, sets the onClickListener and actions,
        // and then adds them to the gridLayout
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

                        //changes the click button's color
                        toggleButtonColor(clickedRow, clickedCol);

                        //changes the adjacent buttons' color (up, left, right, and down)
                        toggleAdjacentButtons(clickedRow, clickedCol);

                        //checks win condition (all buttons are black)
                        if (isGameWon()) {
                            //win condition notifier
                            gridLayout.setBackgroundColor(Color.GREEN);
                        }
                    }

                });

                //add the buttons to the gridLayout
                gridLayout.addView(buttons[i][j]);

                //sets reset button's onClickListener
                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reset button click event
                        resetGrid(v);
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

        //check if the button's text is empty
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

    //helper method to change the colors of adjacent buttons
    private void toggleAdjacentButtons(int row, int col) {
        //acquired inspiration from stackoverflow for this method
        //Citation:
        //Jamiec, Jan 17, 2022. "JavaScript - How to find adjacent tiles in a grid?"
        // StackOverFlow. https://stackoverflow.com/questions/70744440/javascript-how-to-find-adjacent-tiles-in-a-grid

        //creates an array of directions to represent the adjacent buttons
        int[][] directions = { {0, -1}, {-1, 0}, {0, 1}, {1, 0} }; //up, left, down, right

        //iterates through each direction (up, left, down, right)
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            //check if the new position (newRow, newCol) is a valid position in the grid
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

    //helper method to check if the player has won (all buttons are black)
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

    //helper method to assign each button a random color (black or white) when initializing
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

    //helper method to randomize each button's color after clicking the reset button
    public void resetGrid(View v) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col].setBackgroundColor(getRandomColor());
            }
        }

    }

}


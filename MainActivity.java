package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] but = new Button[3][3];

    private boolean player1T = true;
    private int roundC;

    private int P1Points;
    private int P2Points;

    private TextView TVP1;
    private TextView TVP2;
    int A1 = 0;
    int A2 = 0;

    int C1= 0,C2 = 0;


    private boolean botEnable = false;

    Random rand = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TVP1 = findViewById(R.id.text_view_p1);
        TVP2 = findViewById(R.id.text_view_p2);

        for (int i =0 ; i<3 ;i++){
            for (int j = 0; j<3;j++){
                String butID = "button_" + i + j;
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                but[i][j] = findViewById(resID);
                but[i][j].setOnClickListener(this);
            }

        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetG();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v) .getText().toString().equals("")){
            return;
        }
        if(player1T) {
            ((Button) v).setText("X");
            if(botEnable && roundC < 8 && !checkWin()){
                A1 = rand.nextInt(2);
                A2 = rand.nextInt(2);


                if(but[A1][A2].getText().toString().equals("")){
                    but[A1][A2].setText("O");
                }else{
                    for (int i =0 ; i<3 ;i++){
                        for (int j = 0; j<3;j++){
                            if(but[i][j].getText().toString().equals("")){
                                C1++;

                            }
                        }

                    }
                    int[] L1 = new int[C1];
                    int[] L2 = new int[C1];
                    for (int i =0 ; i<3 ;i++){
                        for (int j = 0; j<3;j++){
                            if(but[i][j].getText().toString().equals("")){
                                L1[C2] = i;
                                L2[C2] = j;
                                C2++;
                            }
                        }

                    }
                    int guess = rand.nextInt(C1);
                    A1 = L1[guess];
                    A2 = L2[guess];

                    if(roundC < 8)
                        but[A1][A2].setText("O");

                    C1 = 0;
                    C2 = 0;

                }

                roundC++;
                player1T = !player1T;
            }
        }else{
            ((Button) v).setText("O");
        }
        roundC++;

        if(checkWin()){
            if(player1T){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if (roundC == 9){
            draw();
        }else{
            player1T = !player1T;

        }


    }


    private boolean checkWin() {

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = but[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) &&
                    !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) &&
                    !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) &&
                !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) &&
                !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins(){
        P1Points++;
        Toast.makeText(this, "Player 1 wins, Player 2 is Trash!", Toast.LENGTH_SHORT).show();
        updatePText();
        resetB();
    }
    private void player2Wins(){
        P2Points++;
        Toast.makeText(this, "Player 2 wins, Player 1 is Trash!", Toast.LENGTH_SHORT).show();
        updatePText();
        resetB();
    }
    private void draw() {
        Toast.makeText(this, "It's a draw, you both suck!", Toast.LENGTH_SHORT).show();
        resetB();

    }
    private void updatePText(){
        if(!botEnable) {
            TVP1.setText("Player 1: " + P1Points);
            TVP2.setText("Player 2: " + P2Points);
        }
        if(botEnable){
            TVP1.setText("Player 1: " + P1Points);
            TVP2.setText("The Bot: " + P2Points);
        }
    }
    private void resetB(){
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                but[i][j].setText("");
            }
        }
        roundC = 0;
        player1T = true;
    }
    private void resetG(){
        P1Points = 0;
        P2Points = 0;
        updatePText();
        resetB();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundC", roundC);
        outState.putInt("P1Points", P1Points);
        outState.putInt("P2Points", P2Points);
        outState.putBoolean("player1T", player1T);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundC = savedInstanceState.getInt("roundC");
        P1Points = savedInstanceState.getInt("P1Points");
        P2Points = savedInstanceState.getInt("P2Points");
        player1T = savedInstanceState.getBoolean("player1T");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.bot:
                botEnable = !botEnable;
                TVP2.setText("The Bot: ");
                return true;
            case R.id.names:
                botEnable = !botEnable;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.utente.esercizio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final String tag = "MainActivity";

    private Random random;

    private TextView gameResult;
    private EditText currentNumberView;
    private Button tryButton;

    int number;
    int tries;

    private int gameSeconds;

    Timer timer;
    MyTimerTask myTimerTask;

    class MyTimerTask extends TimerTask{

        @Override
        public void run(){
            gameSeconds++;
            myTimerTask= new MyTimerTask();
            timer.schedule(myTimerTask,1000);
        }
    }


    // metodo per mettere in pausa operazioni ( es: animazioni, musica...)
    @Override
    protected void onPause(){
        super.onPause();

        if(myTimerTask != null) {
            myTimerTask.cancel(); // serve a terminare il timer
            myTimerTask = null;

            resetGameData();
        }
        //Lod.d è usato per scrivere nei registri, questi log vengono compilati ed eliminati in fase di esecuzione
       // Log.d (String tag, string messaggio)
        Log.d(tag, "-----------------onPause()----------------");
    }

    //con questo metodo l'app rilascia quasi tutte le risorse che non sono necessarie mentre lutente non la sta utilizzando
    protected void onStop(){
        super.onStop();

        Log.d(tag, "---------------onStop()---------------");
    }


    //questo è lo stato in cui l'app interagisce con l'utente. l'app rimane in questo stato fino a quando
    //qualcos non viene messa a fuoco dall'app. un evento potrebbe essere lo schermo che si sta spegendo
    protected void onResume(){
        super.onResume();

        Log.d("debug", "-------------------------------onResume---------------");

        myTimerTask= new MyTimerTask();
        timer.schedule(myTimerTask,1000);
    }

    protected void noStart(){
        super.onStart();

        Log.d(tag, "---------------------onStart()-----------------------");
    }


    protected void onCreate(Bundle savedInstanceState) {

        Log.d(tag,"onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tries=0;

        gameSeconds=0;

        random = new Random();
        number= random.nextInt(20)+1;

        timer = new Timer();

        gameResult= findViewById(R.id.gameResult);
        tryButton= findViewById(R.id.tryAction);
        currentNumberView= findViewById(R.id.numberView);

        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
            }
        });
    }


    private void resetGameData(){
        number= random.nextInt(20) +1;
        tries=0;
        gameSeconds=0;
    }

    private void evaluate(){
        tries ++;

        String currentNumberText= currentNumberView.getText().toString();

        int currentNumber= Integer.parseInt(currentNumberText);

        if(currentNumber == number) {
            Intent intent = new Intent(this,ShowResult.class);
            intent.putExtra("triesNumber", Integer.toBinaryString(tries));
            intent.putExtra("finalNumber", currentNumberText);
            intent.putExtra("gameTime", Integer.toString(gameSeconds));

            startActivity(intent);
        }else if( currentNumber > number){
            gameResult.setText("il numero inserito " + currentNumberText+ "è più alto");
        }else {
            gameResult.setText("il numero inserito " + currentNumberText+ "è più basso");
        }
    }
}

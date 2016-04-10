package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char ch= (char) event.getUnicodeChar();
        if(Character.isLetter(ch))
        {
            textUpdate(ch);
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    void textUpdate(char ch)
    {
        TextView text = (TextView) findViewById(R.id.ghostText);
        String S= text.getText() +(ch +"");
        text.setText(S);
//        dictionary.getAnyWordStartingWith(S);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        try {
            InputStream in= getAssets().open("words.txt");
            dictionary = new SimpleDictionary(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button b=(Button) findViewById(R.id.challenge);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challenge();
            }
        });
        Button b1=(Button) findViewById(R.id.replay);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart(null);
            }
        });


        onStart(null);
    }
    void challenge()
    {
        TextView text = (TextView) findViewById(R.id.ghostText);
       String p=(String) text.getText();
        if( p.length()>=4 && dictionary.isWord(p))
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("user challenged and won");
        }
        else{
            TextView label = (TextView) findViewById(R.id.gameStatus);
            if(((String) text.getText()).isEmpty()){
                label.setText("no inout string");
                return;
            }
            String word = dictionary.getAnyWordStartingWith(p);
            if(word == null)
                label.setText("user challenge and won");
            else
                label.setText("user challenge,lost and computer won");

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);

        if((text.getText()).length()>=4 && (dictionary.isWord((String) text.getText())))
        {
            label.setText("computer wins, it is a valid word");
            return;
        }

             String prefix=(String) text.getText();
             String word  = dictionary.getAnyWordStartingWith(prefix);
        if(word.length()!=0)
            {
                text.setText(text.getText()+""+word.charAt(prefix.length()));
                userTurn = true;
                label.setText(USER_TURN);
            }
            else
            {
                label.setText("computer challenges and wins");
//                onStart(null);
//                onStart(null);
            }
        // Do computer turn stuff then make it the user's turn again
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
//        userTurn = true;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        TextView text = (TextView) findViewById(R.id.ghostText);
        outState.putString("prefix", (String) text.getText());

        TextView label = (TextView) findViewById(R.id.gameStatus);
        outState.putString("status", (String) label.getText());


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText(savedInstanceState.getString("prefix"));

        TextView label = (TextView) findViewById(R.id.gameStatus);
        label.setText(savedInstanceState.getString("status"));

        }

    }


package com.domene.tilfeldig.s305471mapp1;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class GameActivity extends Activity {

    private TextView mathProblem, answer, one, two, three, four, five, six, seven, eight, nine, answerToMathProblem, zero, back, rightTV, wrongTV;
    private int currProblem = 0, rightAnswers = 0, wrongAnswers = 0, counter = 0, currMaxR, currMaxF, amountQuestions;
    private View line;
    private String wrong, right;
    private String[] arrayAnswers, arrayQuestions;
    private int[] arrayPos;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private static final String LANG_KEY = "språk";
    private static final String SPM_KEY = "spm";
    private static final String ANT_RIKTIG = "ant_riktig";
    private static final String ANT_FEIL = "ant_feil";
    private static final String TOTAL_ANT_RIKTIG = "total_ant_riktig";
    private static final String TOTAL_ANT_FEIL = "total_ant_feil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareSharedPreferences();
        setLanguageNorwegianNO_RECREATE();
        setLanguageGermanNO_RECREATE();
        setContentView(R.layout.activity_game);
        removeStatusBar();
        getArrayResources();
        shuffle(arrayPos);
        findViewIDs();
        updateTextView();
        setButtonOnClickListeners();
        updateSavedInstaceState(savedInstanceState);
        updateTilNowCounters();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(R.string.avslutte);

                alertDialogBuilder.setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backToStart();
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.nei, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void nextQuestion() {
        currProblem++;
        updateTextView();
        answerToMathProblem.setText("");
    }
    private void updateTextView() {
        String problem_txt = arrayQuestions[arrayPos[currProblem]];
        mathProblem.setText(problem_txt);
    }
    private static void shuffle(int[] arrayPos) {
        int len = arrayPos.length;
        Random rand = new Random();

        for (int i = 0; i < arrayPos.length; i++) {
            int randomValue = i + rand.nextInt(len - i);
            int randomElement = arrayPos[randomValue];
            arrayPos[randomValue] = arrayPos[i];
            arrayPos[i] = randomElement;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("current_answer", answerToMathProblem.getText().toString());
        outState.putString("current_question", arrayQuestions[arrayPos[currProblem]]);
        outState.putIntArray("current_position_array", arrayPos);
        outState.putStringArray("current_answer_array", arrayQuestions);
        outState.putInt("current_ant_right_answ", rightAnswers);
        outState.putInt("current_ant_wrong_answ", wrongAnswers);
        super.onSaveInstanceState(outState);
    }
    private void updateAnswer(String tall) {
        String s = tall + "";
        answerToMathProblem.append(s);
    }
    private void reduceInputAmount() {
        String svar = answerToMathProblem.getText().toString();
        if (svar.length() == 0) {
        } else {
            svar = svar.substring(0, svar.length() - 1);
            answerToMathProblem.setText(svar);
        }
    }
    private void checkAnswer() {
        counter++;
        String svar = answerToMathProblem.getText().toString();

        if (svar.equals(arrayAnswers[arrayPos[currProblem]])) {
            correctAnswer();
            rightAnswers++;
            nextQuestion();
            updateTilNowCounters();
            answerToMathProblem.setHint(R.string.yay);
        } else {
            answerToMathProblem.setHint(R.string.feil);
            wrongAnswer();
            wrongAnswers++;
            updateTilNowCounters();
        }
        if (sf.getInt(SPM_KEY, 1) == counter) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setCancelable(false);
            setFinishOnTouchOutside(false);
            b.setMessage(getString(R.string.ferdig) + ". " + getString(R.string.du_fikk) +" " + rightAnswers +" " + getString(R.string.riktige_svar) +
                    " " +getString(R.string.og) + " " + wrongAnswers + " " + getString(R.string.gale_svar));
            b.setPositiveButton(R.string.tilStart, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    backToStart();
                }
            });
            b.create();
            b.show();
            editor.putInt(ANT_RIKTIG, rightAnswers).apply();
            editor.putInt(ANT_FEIL, wrongAnswers).apply();
            currMaxR = sf.getInt(TOTAL_ANT_RIKTIG, 0);
            currMaxR = rightAnswers + currMaxR;
            currMaxF = sf.getInt(TOTAL_ANT_FEIL, 0);
            currMaxF = wrongAnswers + currMaxF;
            editor.putInt(TOTAL_ANT_RIKTIG, currMaxR).apply();
            editor.putInt(TOTAL_ANT_FEIL, currMaxF).apply();
        }
    }
    private void correctAnswer() {
        int cF = ((ColorDrawable) line.getBackground()).getColor();
        int colorTo = getResources().getColor(R.color.fullGrønn);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), cF, colorTo);
        colorAnimation.setDuration(1000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                line.setBackgroundColor((int) animator.getAnimatedValue());
                line.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            }

        });

        colorAnimation.start();
    }
    private void wrongAnswer() {
        int cF = ((ColorDrawable) line.getBackground()).getColor();
        int colorTo = getResources().getColor(R.color.fullRød);
        answerToMathProblem.setText(null);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), cF, colorTo);
        colorAnimation.setDuration(1000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                line.setBackgroundColor((int) animator.getAnimatedValue());
                line.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            }

        });

        colorAnimation.start();
    }
    private void backToStart() {
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        finish();
    }
    private void findViewIDs(){
        mathProblem = findViewById(R.id.regnestykke);
        one = findViewById(R.id.en);
        two = findViewById(R.id.to);
        three = findViewById(R.id.tre);
        four = findViewById(R.id.fire);
        five = findViewById(R.id.fem);
        six = findViewById(R.id.seks);
        seven = findViewById(R.id.syv);
        eight = findViewById(R.id.åtte);
        nine = findViewById(R.id.ni);
        zero = findViewById(R.id.ingenting);
        back = findViewById(R.id.tilbake);
        answerToMathProblem = findViewById(R.id.svarPaaRegnestykke);
        answer = findViewById(R.id.svar);
        line = findViewById(R.id.strek);
        rightTV = findViewById(R.id.numRightTilNow);
        wrongTV = findViewById(R.id.numWrongTilNow);
        wrong = getString(R.string.galt);
        right = getString(R.string.riktig);
    }
    private void setButtonOnClickListeners(){
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("2");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer("0");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceInputAmount();
            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }
    private void getArrayResources(){
        arrayAnswers = getApplicationContext().getResources().getStringArray(R.array.math_solutions_add);
        arrayQuestions = getApplicationContext().getResources().getStringArray(R.array.math_problems_add);
        arrayPos = getApplicationContext().getResources().getIntArray(R.array.array_positions);
    }
    private void removeStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
    }
    private void setLanguageNorwegianNO_RECREATE(){
        if (sf.getString(LANG_KEY, "").equals("no")) {
            editor.putString(LANG_KEY, "no").apply();
            String lang = "no";
            Context c = getApplicationContext();
            Locale l = new Locale(lang);
            Locale.setDefault(l);
            Resources res = c.getResources();
            Configuration conf = new Configuration(res.getConfiguration());
            conf.locale = l;
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
    }
    private void setLanguageGermanNO_RECREATE(){
        if (sf.getString(LANG_KEY, "").equals("de")) {
            editor.putString(LANG_KEY, "de").apply();
            String lang = "de";
            Context c = getApplicationContext();
            Locale l = new Locale(lang);
            Locale.setDefault(l);
            Resources res = c.getResources();
            Configuration conf = new Configuration(res.getConfiguration());
            conf.locale = l;
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
    }
    private void updateSavedInstaceState(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            answerToMathProblem.setText(savedInstanceState.getString("current_answer"));
            mathProblem.setText(savedInstanceState.getString("current_question"));
            arrayPos = savedInstanceState.getIntArray("current_position_array");
            arrayQuestions = savedInstanceState.getStringArray("current_answer_array");
            rightAnswers = savedInstanceState.getInt("current_ant_right_answ");
            wrongAnswers = savedInstanceState.getInt("current_ant_wrong_answ");
            updateTextView();
            updateTilNowCounters();
        }
    }
    private void prepareSharedPreferences(){
        sf = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sf.edit();
    }
    private void updateTilNowCounters(){
        amountQuestions = sf.getInt(SPM_KEY, 5);
        Log.d("I updateTilNow: ", rightAnswers + " : " + wrongAnswers);
        rightTV = findViewById(R.id.numRightTilNow);
        wrongTV = findViewById(R.id.numWrongTilNow);
        rightTV.setText(rightAnswers + " / " + amountQuestions + " " + right);
        wrongTV.setText(wrongAnswers + " / " + amountQuestions + " " + wrong);
    }
}

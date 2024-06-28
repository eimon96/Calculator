package com.e.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author eimon
 * **Jun 2022**
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button Bt0, Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9;
    private Button BtPlus, BtMinus, BtMult, BtDiv, BtChSign, BtDec, BtEq;
    private Button BtCE, BtC;
//    private Button BtCurrency;
    @SuppressLint("StaticFieldLeak")
    private static TextView TvPrakseis, TvResult, TvHistory, TvCurrency;
    @SuppressLint("StaticFieldLeak")
    private static TextView TvHelperUp, TvHelperDown;
    @SuppressLint("StaticFieldLeak")
    private static Spinner SpCur1, SpCur2;

    public static Map<String, Double> rates;
    public static ArrayList<RetrofitModels.RateModel> ratesList = new ArrayList<>();
    public enum Mode {NORMAL, CURRENCY};
    private Mode currentMode = Mode.NORMAL;

    /**
     * Αρχικοποίηση οθόνης calculator
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
        setListeners();

//        ThreadClass athread = new ThreadClass();
//        new Thread(athread).start();

        playMusic();
    }

    /**
     * Εκκίνηση αναπαραγωγής μουσικής
     * Το τραγούδι παίζει σε λούπα
     */
    private void playMusic()
    {
        MediaPlayerClass.initPlayer(this.getApplicationContext());
        MediaPlayerClass.initLoopBehavior();
    }

    /**
     * onStop: σταματάει την αναπαραγωγή μουσικής όταν η εφαρμογή πάει στο background
     */
    @Override
    protected void onStop()
    {
        super.onStop();
        MediaPlayerClass.pausePlayer();
    }

    /**
     * onRestart: συνεχίζει την αναπαραγωγή μουσικής όταν η εφαρμογή ξαναβρεθεί στο foreground
     */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        MediaPlayerClass.resumePlayer();
    }

    /**
     * onDestroy: τερματίζει την εφαρμογή και σταματάει την αναπαραγωγή μουσικής
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        MediaPlayerClass.releasePlayer();
    }

    /**
     * Πραγματοποιεί ενέργειες στο πάτημα των κουμπιών
     *
     * @param v: view
     */
    public void onClick(View v)
    {
        if (v == Bt0)
        {
            if (TvPrakseis.getText().toString().equals("0"))
            {
                return;
            }
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "0", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt1)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "1", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt2)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "2", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt3)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "3", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt4)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "4", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt5)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "5", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt6)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "6", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt7)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "7", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt8)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "8", TvCurrency);
            CalculationUtils.solve(currentMode);
        }
        if (v == Bt9)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "9", TvCurrency);
            CalculationUtils.solve(currentMode);
        }

        if (v == BtPlus)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "+", TvCurrency);
        }
        if (v == BtMinus)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "-", TvCurrency);
        }
        if (v == BtMult)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "*", TvCurrency);
        }
        if (v == BtDiv)
        {
            ArithmeticsUtils.updatePrakseisView(TvPrakseis, "/", TvCurrency);
        }
        if (v == BtDec)
        {
            ArithmeticsUtils.addDotToCalc(TvPrakseis, TvCurrency);
            CalculationUtils.solve(currentMode);
        }

        if (v == BtEq)
        {
            CalculationUtils.solve(currentMode);
            switchViews();
        }
        if (v == BtChSign)
        {
            ArithmeticsUtils.changeSign(TvPrakseis);
            CalculationUtils.solve(currentMode);
        }

        if (v == BtC)
        {
            clearAll();
        }
        if (v == BtCE)
        {
            clearLastEntry();
            CalculationUtils.solve(currentMode);
        }
//        if (v == BtCurrency)
//        {
//            pressedBtCurrency();
//        }
    }

    /**
     * Για όταν πατιέται το κουμπί της μετατροπής νομισμάτων
     * Κάνει τους απαραίτητους ελέγχους
     */
//    private void pressedBtCurrency()
//    {
//        if (currentMode.equals(Mode.NORMAL) && !hasInternet())
//        {
//            Toast.makeText(this, "No Internet!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (ArithmeticsUtils.lastEntryParenth(TvPrakseis) || ArithmeticsUtils.lastEntrySign(TvPrakseis))
//        {
//            return;
//        }
//
//        // Καλεί το API κάθε φορά που πάει σε currency mode γιατί μπορεί να έχει αλλάξει η ισοτιμία
//        if (currentMode.equals(Mode.NORMAL))
//        {
//            ThreadClass athread = new ThreadClass();
//            new Thread(athread).start();
//
//            CalculationUtils.solve(currentMode);
//            switchViews();
//
//            TvResult.setTypeface(null, Typeface.NORMAL);
//        }
//        else
//        {
//            TvResult.setText(TvCurrency.getText().toString());
//            TvResult.setTypeface(null, Typeface.ITALIC);
//        }
//
//            CurrencyViewUtils.switchColor(this.getApplicationContext(), currentMode, BtCurrency);
//        CurrencyViewUtils.switchButtons(this.getApplicationContext(), currentMode,
//                BtPlus, BtMinus, BtMult, BtDiv, BtChSign, BtEq, SpCur1, SpCur2);
//
//        ConstraintLayout cl = findViewById(R.id.myConstraint);
//        CurrencyViewUtils.switchTopViews(this.getApplicationContext(),
//                currentMode, TvHistory, TvPrakseis, TvCurrency, cl);
//
//        currentMode = CurrencyViewUtils.switchMode(currentMode);
//    }

    /**
     * Ελέγχει αν υπάρχει σύνδεση στο Internet
     *
     * @return true αν ναι (WiFi/Mobile Data), false αλλιώς
     */
    private boolean hasInternet()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    /**
     * Εναλλάσσει τα views
     * TvPrakseis -> TvHistory
     * TvResult -> TvPrakseis
     * To TvCurrency παίρνει το κείμενο του TvPrakseis
     */
    private void switchViews()
    {
        boolean isResultInfinite = TvResult.getText().toString().endsWith("Infinity");
        boolean isResultNaN = TvResult.getText().toString().equals("NaN");
        boolean isZeroDigit = TvPrakseis.getText().toString().equals("0");

        if (isResultInfinite || isResultNaN || isZeroDigit)
        {
            return;
        }

        if (!ArithmeticsUtils.lastEntrySign(TvPrakseis))
        {
            TvHistory.setText(TvPrakseis.getText().toString());
            TvPrakseis.setText(TvResult.getText().toString());
            TvCurrency.setText(TvPrakseis.getText().toString());
        }
    }

    /**
     * Καλείται όταν πατηθεί το κουμπί C
     * Διαγράφει τα πάντα
     */
    private void clearAll()
    {
        TvHistory.setText("");
        TvPrakseis.setText("");
        TvResult.setText("0");
        TvCurrency.setText("");
    }

    /**
     * Καλείται όταν πατηθεί το κουμπί CE
     * Διαγράφει τον τελευταίο χαρακτήρα (είτε ψηφίο είτε σύμβολο)
     */
    private void clearLastEntry()
    {
        if (TvPrakseis.getText().toString().equals(""))
        {
            return;
        } else if (TvPrakseis.getText().toString().equals("Infinity") || TvPrakseis.getText().toString().equals("-Infinity"))
        {
            clearAll();
            return;
        }
        String oldStr = TvPrakseis.getText().toString();
        String newStr;

        if (ArithmeticsUtils.lastEntryParenth(TvPrakseis))
        {
            int openParenth = oldStr.lastIndexOf("(");
            String justTheNum = oldStr.substring(openParenth + 2, oldStr.length() - 1);
            newStr = oldStr.substring(0, openParenth) + justTheNum;
        } else
        {
            newStr = oldStr.substring(0, oldStr.length() - 1);
        }

        TvPrakseis.setText(newStr);
        TvCurrency.setText(TvPrakseis.getText().toString());
        if (newStr.isEmpty())
        {
            TvResult.setText("0");
        }
    }

    /**
     * Σύνδεση των κουμπιών και των textview του κώδικα με τα αντίστοιχα του layout
     */
    private void initializeButtons()
    {
        Bt0 = findViewById(R.id.Bt0);
        Bt1 = findViewById(R.id.Bt1);
        Bt2 = findViewById(R.id.Bt2);
        Bt3 = findViewById(R.id.Bt3);
        Bt4 = findViewById(R.id.Bt4);
        Bt5 = findViewById(R.id.Bt5);
        Bt6 = findViewById(R.id.Bt6);
        Bt7 = findViewById(R.id.Bt7);
        Bt8 = findViewById(R.id.Bt8);
        Bt9 = findViewById(R.id.Bt9);
        BtPlus = findViewById(R.id.BtProsthesh);
        BtMinus = findViewById(R.id.BtAfairesh);
        BtMult = findViewById(R.id.BtPollaplasiasmos);
        BtDiv = findViewById(R.id.BtDiairesh);
        BtChSign = findViewById(R.id.BtSynPlin);
        BtDec = findViewById(R.id.BtTelia);
        BtEq = findViewById(R.id.BtEqual);
        BtC = findViewById(R.id.BtC);
        BtCE = findViewById(R.id.BtCE);
//        BtCurrency = findViewById(R.id.BtCurrency);

        TvPrakseis = findViewById(R.id.TvPrakseis);
        TvResult = findViewById(R.id.TvResult);
        TvHistory = findViewById(R.id.TvHistory);
        TvCurrency = findViewById(R.id.TvCurrency);
        TvHelperUp = findViewById(R.id.TvHelperUp);
        TvHelperDown = findViewById(R.id.TvHelperDown);

        SpCur1 = findViewById(R.id.SpCur1);
        SpCur2 = findViewById(R.id.SpCur2);
    }

    /**
     * set listeners στα buttons
     * αρχικοποίηση των spinners
     */
    private void setListeners()
    {
        Bt0.setOnClickListener(this);
        Bt1.setOnClickListener(this);
        Bt2.setOnClickListener(this);
        Bt3.setOnClickListener(this);
        Bt4.setOnClickListener(this);
        Bt5.setOnClickListener(this);
        Bt6.setOnClickListener(this);
        Bt7.setOnClickListener(this);
        Bt8.setOnClickListener(this);
        Bt9.setOnClickListener(this);
        BtPlus.setOnClickListener(this);
        BtMinus.setOnClickListener(this);
        BtMult.setOnClickListener(this);
        BtDiv.setOnClickListener(this);
        BtChSign.setOnClickListener(this);
        BtDec.setOnClickListener(this);
        BtEq.setOnClickListener(this);
        BtC.setOnClickListener(this);
        BtCE.setOnClickListener(this);
//        BtCurrency.setOnClickListener(this);

        SpCur1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                ConverterUtils.printExchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        ArrayAdapter dataAdapter = new ArrayAdapter(SpCur1.getContext(), R.layout.spinner_item, new ArrayList<>());
        SpCur1.setAdapter(dataAdapter);

        SpCur2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                ConverterUtils.printExchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        ArrayAdapter dataAdapter2 = new ArrayAdapter(SpCur2.getContext(), R.layout.spinner_item, new ArrayList<>());
        SpCur2.setAdapter(dataAdapter2);
    }

    // Getters
    public static TextView getTvHelperUp()
    {
        return TvHelperUp;
    }
    public static TextView getTvHelperDown()
    {
        return TvHelperDown;
    }
    public static TextView getTvPrakseis()
    {
        return TvPrakseis;
    }
    public static TextView getTvResult()
    {
        return TvResult;
    }
    public static Spinner getSpCur1()
    {
        return SpCur1;
    }
    public static Spinner getSpCur2()
    {
        return SpCur2;
    }
}
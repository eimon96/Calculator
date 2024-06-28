package com.e.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/**
 * CurrencyViewUtils class ασχολείται με την τροποποίηση των view μεταξύ normal & currency mode
 */
public class CurrencyViewUtils
{
    @SuppressLint("StaticFieldLeak")
    private static final TextView TvHelperUp = MainActivity.getTvHelperUp();
    @SuppressLint("StaticFieldLeak")
    private static final TextView TvHelperDown = MainActivity.getTvHelperDown();

    /**
     * Αλλαζει το χρώμα του Button αναλόγως το Mode
     * @param co
     * @param currentMode
     * @param BtCurrency
     */
    public static void switchColor(Context co, MainActivity.Mode currentMode, Button BtCurrency)
    {
        if (currentMode.equals(MainActivity.Mode.NORMAL))
        {
            BtCurrency.setBackgroundColor(co.getResources().getColor(R.color.white_ec));
            return;
        }
        BtCurrency.setBackgroundColor(co.getResources().getColor(R.color.golden));
    }

    /**
     * Enable/Disable buttons ανάλογα με το Mode
     * Δε θα γίνονται πράξεις στο Currency Mode παρά μόνο στο Normal Mode
     * @param co
     * @param currentMode
     * @param BtPlus
     * @param BtMinus
     * @param BtMult
     * @param BtDiv
     * @param BtChSign
     * @param BtEq
     * @param SpCur1
     * @param SpCur2
     */
    public static void switchButtons(Context co, MainActivity.Mode currentMode, Button BtPlus, Button BtMinus,
                                     Button BtMult, Button  BtDiv, Button BtChSign, Button BtEq, Spinner SpCur1, Spinner SpCur2)
    {
        if (currentMode.equals(MainActivity.Mode.NORMAL))
        {
            BtPlus.setEnabled(false);
            BtPlus.setBackgroundColor(co.getResources().getColor(R.color.grey));
            BtMinus.setEnabled(false);
            BtMinus.setBackgroundColor(co.getResources().getColor(R.color.grey));
            BtMult.setEnabled(false);
            BtMult.setBackgroundColor(co.getResources().getColor(R.color.grey));
            BtDiv.setEnabled(false);
            BtDiv.setBackgroundColor(co.getResources().getColor(R.color.grey));
            BtChSign.setEnabled(false);
            BtChSign.setBackgroundColor(co.getResources().getColor(R.color.grey));
            BtEq.setEnabled(false);
            BtEq.setBackgroundColor(co.getResources().getColor(R.color.grey));

            SpCur1.setVisibility(View.VISIBLE);
            SpCur2.setVisibility(View.VISIBLE);

            return;
        }
        BtPlus.setEnabled(true);
        BtPlus.setBackgroundColor(co.getResources().getColor(R.color.blue_25));
        BtMinus.setEnabled(true);
        BtMinus.setBackgroundColor(co.getResources().getColor(R.color.blue_25));
        BtMult.setEnabled(true);
        BtMult.setBackgroundColor(co.getResources().getColor(R.color.blue_25));
        BtDiv.setEnabled(true);
        BtDiv.setBackgroundColor(co.getResources().getColor(R.color.blue_25));
        BtChSign.setEnabled(true);
        BtChSign.setBackgroundColor(co.getResources().getColor(R.color.blue_25));
        BtEq.setEnabled(true);
        BtEq.setBackgroundColor(co.getResources().getColor(R.color.blue_25));

        SpCur1.setVisibility(View.GONE);
        SpCur2.setVisibility(View.GONE);
    }

    /**
     * Ενναλλαγή Mode
     * @param currentMode
     * @return Mode
     */
    public static MainActivity.Mode switchMode(MainActivity.Mode currentMode)
    {
        if (currentMode.equals(MainActivity.Mode.NORMAL))
        {
            return MainActivity.Mode.CURRENCY;
        }
        return MainActivity.Mode.NORMAL;
    }

    /**
     * Απόκρυψη/Εμφάνιση textviews ανάλογα με το Mode
     * @param co
     * @param currentMode
     * @param TvHistory
     * @param TvPrakseis
     * @param TvCurrency
     */
    public static void switchTopViews(Context co, MainActivity.Mode currentMode,
                                      TextView TvHistory, TextView TvPrakseis, TextView TvCurrency,
                                      ConstraintLayout cl)
    {
        if (currentMode.equals(MainActivity.Mode.NORMAL))
        {
            TvHistory.setVisibility(View.GONE);
            TvHistory.setBackgroundColor(co.getResources().getColor(R.color.grey));
            TvPrakseis.setVisibility(View.GONE);
            TvPrakseis.setBackgroundColor(co.getResources().getColor(R.color.grey));
            TvCurrency.setVisibility(View.VISIBLE);
            TvCurrency.setBackground(co.getResources().getDrawable(R.drawable.round_textview));
            TvHelperUp.setVisibility(View.VISIBLE);
            TvHelperDown.setVisibility(View.VISIBLE);

            changeConstraint(cl, R.id.TvResult, R.id.guideline_c1);
            changeConstraint(cl, R.id.TvCurrency, R.id.guideline_c1);

            return;
        }
        TvCurrency.setVisibility(View.GONE);
        TvHistory.setVisibility(View.VISIBLE);
        TvHistory.setBackground(co.getResources().getDrawable(R.drawable.round_textview));
        TvPrakseis.setVisibility(View.VISIBLE);
        TvPrakseis.setBackground(co.getResources().getDrawable(R.drawable.round_textview));
        TvHelperUp.setVisibility(View.GONE);
        TvHelperDown.setVisibility(View.GONE);

        changeConstraint(cl, R.id.TvResult, R.id.myConstraint);
        changeConstraint(cl, R.id.TvCurrency, R.id.myConstraint);

    }

    /**
     * Δημιουργεί νέο constraint μεταξύ δύο view
     * @param cl
     * @param Tv1
     * @param Tv2
     */
    private static void changeConstraint(ConstraintLayout cl, int Tv1, int Tv2)
    {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(cl);
        constraintSet.connect(Tv1, ConstraintSet.START, Tv2, ConstraintSet.START,10);
        constraintSet.applyTo(cl);
    }
}

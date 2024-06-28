package com.e.calculator;

import android.annotation.SuppressLint;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

/**
 * ConverterUtils class ασχολείται με τις μετατροπές της ισοτιμίας νομισμάτων
 */
public class ConverterUtils
{
    @SuppressLint("StaticFieldLeak")
    private static final TextView TvPrakseis = MainActivity.getTvPrakseis();
    @SuppressLint("StaticFieldLeak")
    private static final TextView TvResult = MainActivity.getTvResult();
    private static Map<String, Double> rates = MainActivity.rates;
    private static ArrayList<RetrofitModels.RateModel> ratesList = MainActivity.ratesList;

    /**
     * Μετατροπή από ένα νόμισμα σε ένα άλλο
     * @return είτε την αντίστοιχη τιμή είτε 0.0 σε περίπτωση σφάλματος
     */
    public static Double convertFromTo(String origin, String target, Double amount)
    {
        try
        {
            Double originRate = rates.get(origin);
            Double targetRate = rates.get(target);

            return (amount * targetRate / originRate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * Εμφανίζει το αποτέλεσμα της ισοτιμίας στο TvResult
     */
    @SuppressLint("SetTextI18n")
    public static void printExchange()
    {
        try
        {
            Double amount = 0.0;

            if (!TvPrakseis.getText().toString().equals(""))
            {
                amount = Double.parseDouble(TvPrakseis.getText().toString());
            }
            String origin = MainActivity.getSpCur2().getSelectedItem().toString();
            String target = MainActivity.getSpCur1().getSelectedItem().toString();
            TvResult.setText(convertFromTo(origin, target, amount).toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

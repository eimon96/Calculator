package com.e.calculator;

import android.widget.TextView;

/**
 * ArithmeticsUtils class aσχολείται με την τροποποίηση του TvPrakseis (και κατ'επέκταση του TvCurrency)
 */
public class ArithmeticsUtils
{
    /**
     * Προσθήκη δεκαδικής τελείας "."
     * Κάνει τους απαραίτητους ελέγχους
     * Αν προηγουμένως δεν υπάρχει αριθμός, γράφει "0."
     * Παραμέτρους έχει τα προς τροποποίηση TextViews
     * @param TvPrakseis
     * @param TvCurrency
     */
    public static void addDotToCalc(TextView TvPrakseis, TextView TvCurrency)
    {
        String input = TvPrakseis.getText().toString();

        if (lastEntrySign(TvPrakseis) || input.equals(""))
        {
            updatePrakseisView(TvPrakseis, "0.", TvCurrency);
        }
        else if (input.endsWith(".") || alreadyDecimal(TvPrakseis))
        {
            return;
        }
        else
        {
            updatePrakseisView(TvPrakseis, ".", TvCurrency);
        }
    }

    /**
     * Ελέγχει αν ο τελευταίος χαρακτήρας είναι σύμβολο + = * /
     * @param TvPrakseis το προς έλεγχο textview
     * @return true αν ναι, false αλλιώς
     */
    public static boolean lastEntrySign(TextView TvPrakseis)
    {
        String[] symbols = {"+","-","*","/"};
        for (String symbol : symbols)
        {
            if (TvPrakseis.getText().toString().endsWith(symbol))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Ελέγχει αν ο τελευταίος χαρακτήρας είναι παρένθεση )
     * @param TvPrakseis το προς έλεγχο TextView
     * @return true αν είναι, false αλλιώς
     */
    public static boolean lastEntryParenth(TextView TvPrakseis)
    {
        if (TvPrakseis.getText().toString().endsWith(")"))
        {
            return true;
        }
        return false;
    }

    /**
     * Ανανεώνει το textview με τα προς εκτέλεση στοιχεία
     * @param strToAdd
     * @param TvPrakseis
     * @param TvCurrency
     */
    public static void updatePrakseisView(TextView TvPrakseis, String strToAdd, TextView TvCurrency)
    {
        String oldStr = TvPrakseis.getText().toString();
        String newStr;
        final boolean smbToAdd = (strToAdd.equals("+")) || (strToAdd.equals("-")) || (strToAdd.equals("*")) || (strToAdd.equals("/"));

        if ((lastEntryParenth(TvPrakseis) && !smbToAdd))
        {
            return;
        }

        if (smbToAdd && oldStr.equals(""))
        {
            oldStr = "0" + oldStr;
        }

        if (lastEntrySign(TvPrakseis))
        {
            if (smbToAdd)
            {
                newStr = oldStr.substring(0, oldStr.length() - 1) + strToAdd;
            }
            else
            {
                newStr = oldStr + strToAdd;
            }
        }
        else
        {
            newStr = oldStr + strToAdd;
        }

        TvPrakseis.setText(newStr);
        cutZeros(TvPrakseis);
        TvCurrency.setText(TvPrakseis.getText().toString());
    }

    /**
     * Μετατρέπει τον τελευταίο αριθμό από θετικό σε αρνητικό και αντιστρόφως
     * @param TvPrakseis το προς τροποποίηση TextView
     */
    public static void changeSign(TextView TvPrakseis)
    {
        if (TvPrakseis.getText().toString().equals("0"))
        {
            return;
        }

        if (lastEntrySign(TvPrakseis) || TvPrakseis.getText().toString().equals(""))
        {
            return;
        }
        String input  = TvPrakseis.getText().toString();
        String chInput;

        // Αν τελειώνει με παρένθεση είναι αρνητικός  πχ ...(-96)
        // Θα μετατραπεί σε θετικό
        if (input.endsWith(")"))
        {
            int startingPoint = input.lastIndexOf("(");
            int endingPoint = input.lastIndexOf(")");
            chInput = input.substring(0, startingPoint);
            chInput += input.substring(startingPoint + 2, endingPoint);
        }
        // Αν δεν τελειώνει με παρένθεση είναι είτε θετικός πχ ...13
        // είτε ακολουθία ενός αριθμού πχ -9 -> χωρίς παρένθεση
        else
        {
            int startingPoint = lastIndexOfSymbol(TvPrakseis);

            if (startingPoint != 0)
            {
                chInput = input.substring(0, startingPoint + 1);

                chInput += "(-" + input.substring(startingPoint + 1);
                chInput += ")";
            }
            // Αν στην οθόνη υπάρχει μόνο ένας αριθμός και τίποτα άλλο
            else
            {
                if (!input.startsWith("-"))
                {
                    chInput = "(-" + input + ")";
                }
                else
                {
                    chInput = input.substring(1);
                }
            }
        }

        TvPrakseis.setText(chInput);
    }

    /**
     * @return τη θέση του τελευταίου συμβόλου του textview
     * @param TvPrakseis
     */
    public static int lastIndexOfSymbol(TextView TvPrakseis)
    {
        String input  = TvPrakseis.getText().toString();
        String[] symbols = {"+","-","*","/"};
        int lastIndex = 0;
        for (String symbol : symbols)
        {
            if (lastIndex < input.lastIndexOf(symbol))
            {
                lastIndex = input.lastIndexOf(symbol);
            }
        }
        return lastIndex;
    }

    /**
     * Ελέγχει αν ο τελευταίος αριθμός είναι ήδη δεκαδικός
     * @param TvPrakseis το προς έλεγχο TextView
     * @return true αν είναι, false αλλιώς
     */
    public static boolean alreadyDecimal(TextView TvPrakseis)
    {
        String input = TvPrakseis.getText().toString();
        int indexOfSmb = lastIndexOfSymbol(TvPrakseis);

        if (indexOfSmb != -1)
        {
            String lastNum =  input.substring(indexOfSmb + 1);
            return lastNum.contains(".");
        }
        else
        {
            return input.contains(".");
        }
    }

    /**
     * Κόβει το μηδενικό από την αρχή των μη δεκαδικών αριθμών σε ένα δοθεν TextView
     * πχ 02 -> 2
     * @param TvPrakseis
     */
    public static void cutZeros(TextView TvPrakseis)
    {
        String input = TvPrakseis.getText().toString();
        if (alreadyDecimal(TvPrakseis))
        {
            return;
        }

        int indexOfSmb = lastIndexOfSymbol(TvPrakseis);

        String lastNum;
        if (lastIndexOfSymbol(TvPrakseis)!=0)
        {
            lastNum = input.substring(indexOfSmb + 1);
        }
        else
        {
            lastNum = input.substring(indexOfSmb);
        }

        if (lastNum.length() >= 2 && lastNum.startsWith("0") && !lastEntrySign(TvPrakseis))
        {
            String zlastNum = lastNum.substring(1);
            TvPrakseis.setText(input.substring(0, input.length() - lastNum.length()).concat(zlastNum));
        }
    }
}

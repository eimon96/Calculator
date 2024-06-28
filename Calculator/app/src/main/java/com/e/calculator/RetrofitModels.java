package com.e.calculator;

import java.util.Map;

public class RetrofitModels
{
    public static class RatesModel
    {
        Map<String, Double> rates;
    }

    /**
     * Αντικείμενο RateModel
     */
    public static class RateModel
    {
        String currency;
        Double exchange;

        public RateModel(String currency, Double exchange)
        {
            this.currency = currency;
            this.exchange = exchange;
        }
    }

}

//package com.e.calculator;
//
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import java.util.Map;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * Call API σε νέο thread
// */
//public class ThreadClass implements Runnable
//{
//    /**
//     * O κώδικας που θα τρέξει στο thread
//     */
//    @Override
//    public void run()
//    {
//        callApi(MainActivity.getSpCur1(), MainActivity.getSpCur2());
//    }
//
//    /**
//     * Κάλεσπα API για τα νομίσματα και τις ισοτιμίες
//     * Βάζει τα νομίσματα στα δυο Spinner
//     */
//    private void callApi(Spinner SpCur1, Spinner SpCur2)
//    {
//        RetrofitMethods retrofitMethods =  RetrofitClient.getRetrofitInstance().create(RetrofitMethods.class);
//        Call<RetrofitModels.RatesModel> call = retrofitMethods.getAllData();
//
//        call.enqueue(new Callback<RetrofitModels.RatesModel>()
//        {
//            @Override
//            public void onResponse(Call<RetrofitModels.RatesModel> call, Response<RetrofitModels.RatesModel> response)
//            {
//                // in case key is expired or whatever
//                try
//                {
//                    MainActivity.rates = response.body().rates;
//                    for (Map.Entry<String, Double> entry : MainActivity.rates.entrySet())
//                    {
//                        String key = entry.getKey();
//                        Double value = entry.getValue();
//                        RetrofitModels.RateModel rateModel = new RetrofitModels.RateModel(key, value);
//                        MainActivity.ratesList.add(rateModel);
//                    }
//
//                    ArrayAdapter dataAdapter = new ArrayAdapter(SpCur1.getContext(), R.layout.spinner_item, MainActivity.rates.keySet().toArray());
//                    SpCur1.setAdapter(dataAdapter);
//                    SpCur1.setSelection(21);
//                    ArrayAdapter dataAdapter2 = new ArrayAdapter(SpCur2.getContext(), R.layout.spinner_item, MainActivity.rates.keySet().toArray());
//                    SpCur2.setAdapter(dataAdapter2);
//                    SpCur2.setSelection(46);
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RetrofitModels.RatesModel> call, Throwable t)
//            {
//                t.printStackTrace();
//            }
//        });
//    }
//}

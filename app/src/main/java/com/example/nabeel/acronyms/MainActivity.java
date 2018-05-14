package com.example.nabeel.acronyms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nabeel.acronyms.data.remote.RemoteDataSource;
import com.example.nabeel.acronyms.model.AcronymResult;
import com.example.nabeel.acronyms.model.Lf;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivityTag";

    EditText et_acronym;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_acronym = findViewById(R.id.et_acronym);
        tv_result = findViewById(R.id.tv_text);
    }

    public void getAcronyms(View view) {

        RemoteDataSource.getAcronymResult(et_acronym.getText().toString()).enqueue(new Callback<AcronymResult[]>() {
            @Override
            public void onResponse(Call<AcronymResult[]> call, Response<AcronymResult[]> response) {
                Log.d(TAG, "onResponse: ");
                String result = "";

                AcronymResult[] acronymResults = response.body();
                if(acronymResults==null || acronymResults.length==0)
                    result = "No results found for acronym. Please try a different acronym.";
                else {
                    AcronymResult acronymResult = acronymResults[0];
                    if (acronymResult == null) {
                        result = "No results found for acronym. Please try a different acronym.";
                    } else {
                        result = "Results for " + acronymResult.getSf() + "\n";
                        for (Lf lf : acronymResult.getLfs())
                            result += "-" + lf.getLf() + " mentioned " + lf.getFreq() + " times\n";
                    }
                }

                tv_result.setText(result);
            }

            @Override
            public void onFailure(Call<AcronymResult[]> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                tv_result.setText("No response from server. Please try again later.");
            }
        });

    }
}

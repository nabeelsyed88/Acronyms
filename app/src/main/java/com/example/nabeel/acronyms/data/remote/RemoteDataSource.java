package com.example.nabeel.acronyms.data.remote;

import com.example.nabeel.acronyms.model.AcronymResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nabeel on 5/13/2018.
 */

public class RemoteDataSource {

    public static final String BASE_URL = "http://www.nactem.ac.uk/software/acromine/";

    public static Retrofit create() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public static Call<AcronymResult[]> getAcronymResult(String acronym) {
        Retrofit retrofit = create();
        RemoteService service = retrofit.create(RemoteService.class);
        return service.getAcronym(acronym);
    }


    interface RemoteService {
        @GET("dictionary.py")
        Call<AcronymResult[]> getAcronym(@Query("sf") String acronym);
    }

}

package com.alvindizon.basejavaapp.data.network.api;

import com.alvindizon.basejavaapp.data.network.model.SampleResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

// TODO add REST API calls as needed
public interface SampleApi {

    // GET /api/some/thing?someQuery={some_query}
    @GET
    Single<SampleResponse> getSomething(
            @Url String url,
            @Header("authorization") String authorization,
            @Query("someQuery") String someQuery);

}

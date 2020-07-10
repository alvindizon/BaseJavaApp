package com.alvindizon.basejavaapp.data.network.model;

import com.squareup.moshi.Json;

// TODO modify field names as needed
public class SampleResponse {

    @Json(name = "something")
    private String something;

    @Json(name = "someInt")
    private Integer someInt;

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    public Integer getSomeInt() {
        return someInt;
    }

    public void setSomeInt(Integer someInt) {
        this.someInt = someInt;
    }
}

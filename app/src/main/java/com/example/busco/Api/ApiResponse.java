package com.example.busco.Api;

import com.squareup.moshi.Json;

import java.util.List;

public class ApiResponse {
    @Json(name="responseSucessfull")
    private final boolean responseSucessfull;

    @Json(name="description")
    private final String description;

    @Json(name="object")
    private final List<Object> object;

    @Json(name="aditionalInformation")
    private final Object aditionalInformation;

    public boolean isResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getObject() {
        return object;
    }

    public Object getAditionalInformation() {
        return aditionalInformation;
    }

    public ApiResponse(boolean responseSucessfull, String description, List<Object> object, Object aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }
}

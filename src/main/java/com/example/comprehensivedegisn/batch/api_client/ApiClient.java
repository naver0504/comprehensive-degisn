package com.example.comprehensivedegisn.batch.api_client;

public interface ApiClient<T, R> {

    String createUrl(T t);
    R callApi(T t);
}

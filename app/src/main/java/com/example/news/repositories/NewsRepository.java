package com.example.news.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.news.NewsApp;
import com.example.news.di.AppComponent;
import com.example.news.di.DaggerAppComponent;
import com.example.news.interfaces.NewsApi;
import com.example.news.models.NewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private NewsApi newsApi;
    private MutableLiveData<NewsResponse> topNewsData;
    private MutableLiveData<NewsResponse> newsByWordData;

    public NewsRepository(AppComponent appComponent) {
//        newsApi = DaggerAppComponent.create().getApiInterface();
        newsApi = appComponent.getApiInterface();
        topNewsData = new MutableLiveData<>();
        newsByWordData = new MutableLiveData<>();
    }


    public LiveData<NewsResponse> getTopNews(String country, String key) {
        newsApi.getTopNews(country, key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null)
                        topNewsData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
            }
        });

        return topNewsData;
    }

    public LiveData<NewsResponse> getNewsByWord(String word, String key) {
        newsApi.getNewsByWord(word, key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        newsByWordData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
            }
        });

        return newsByWordData;
    }
}

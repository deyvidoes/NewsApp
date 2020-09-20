package com.example.news.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.di.AppComponent;
import com.example.news.models.NewsResponse;
import com.example.news.repositories.NewsRepository;


public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;

    public NewsViewModel(AppComponent appComponent) {
        newsRepository = new NewsRepository(appComponent);
    }

    public LiveData<NewsResponse> getTopNewsData(String country, String key) {
        return newsRepository.getTopNews(country, key);
    }

    public LiveData<NewsResponse> getNewsByWordData(String country, String key) {
        return newsRepository.getNewsByWord(country, key);
    }
}

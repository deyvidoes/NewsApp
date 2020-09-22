package com.example.news.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.news.interfaces.NewsApi;
import com.example.news.models.NewsResponse;
import com.example.news.repositories.NewsRepository;

import javax.inject.Inject;


public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;

    @Inject
    public NewsViewModel(NewsApi newsApi) {
        newsRepository = new NewsRepository(newsApi);
    }

    public LiveData<NewsResponse> getTopNewsData(String country, String key) {
        return newsRepository.getTopNews(country, key);
    }

    public LiveData<NewsResponse> getNewsByWordData(String country, String key) {
        return newsRepository.getNewsByWord(country, key);
    }
}

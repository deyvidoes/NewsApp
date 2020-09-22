package com.example.news.di;

import com.example.news.interfaces.NewsApi;
import com.example.news.viewmodels.NewsViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsViewModelModule {

    @Singleton
    @Provides
    static NewsViewModel provideNewsViewModel(NewsApi newsApi) {
        return new NewsViewModel(newsApi);
    }
}

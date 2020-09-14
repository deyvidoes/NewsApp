package com.example.news.di;

import com.example.news.interfaces.NewsApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = RetrofitModule.class)
public interface AppComponent {

    NewsApi getApiInterface();

}

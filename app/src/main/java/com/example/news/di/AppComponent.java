package com.example.news.di;

import com.example.news.interfaces.NewsApi;
import com.example.news.ui.home.HomeFragment;
import com.example.news.ui.search.SearchFragment;
import com.example.news.viewmodels.NewsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class,
        NewsViewModelModule.class})
public interface AppComponent {

    NewsApi getApiInterface();

    NewsViewModel getNewsViewModel();

    void inject(HomeFragment homeFragment);

    void inject(SearchFragment searchFragment);

}

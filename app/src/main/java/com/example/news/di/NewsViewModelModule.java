package com.example.news.di;

import com.example.news.interfaces.NewsApi;
import com.example.news.util.Constants;
import com.example.news.viewmodels.NewsViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NewsViewModelModule {

    @Singleton
    @Provides
    static NewsViewModel provideNewsViewModel(NewsApi newsApi){
        return new NewsViewModel(newsApi);
    }
}

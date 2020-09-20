package com.example.news.di;

import com.example.news.interfaces.NewsApi;
import com.example.news.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Singleton
    @Provides
    static NewsApi provideNewsApi(Retrofit retrofit){
        return retrofit.create(NewsApi.class);
    }

    @Singleton
    @Provides
    static OkHttpClient provideHttpClient(){
        return new OkHttpClient().newBuilder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .build();
    }

    @Singleton
    @Provides
    static GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    static RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient,
                                    GsonConverterFactory gsonConverterFactory,
                                    RxJavaCallAdapterFactory rxJavaCallAdapterFactory){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

}

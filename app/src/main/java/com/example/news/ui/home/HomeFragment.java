package com.example.news.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.NewsApp;
import com.example.news.R;
import com.example.news.adapters.NewsAdapter;
import com.example.news.models.Article;
import com.example.news.util.Constants;
import com.example.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment {
    private NewsAdapter mRecyclerAdapter;
    private List<Article> mArticles = new ArrayList<>();

    @Inject
    NewsViewModel mNewsViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((NewsApp) requireActivity().getApplication()).getAppComponent().inject(this);
        initRecyclerView(view);
        updateData();
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.top_news_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerAdapter = new NewsAdapter(view.getContext(), mArticles);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    //TODO: Allow user to pick different countries
    private void updateData() {
        mNewsViewModel.getTopNewsData(Constants.COUNTRY_US).observe(getViewLifecycleOwner(), newsResponse -> {
            mArticles.clear();
            mArticles.addAll(newsResponse.getArticles());
            mRecyclerAdapter.notifyDataSetChanged();
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
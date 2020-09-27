package com.example.news.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.NewsApp;
import com.example.news.R;
import com.example.news.adapters.NewsAdapter;
import com.example.news.models.Article;
import com.example.news.ui.SettingsActivity;
import com.example.news.util.Constants;
import com.example.news.util.CountryAbbreviationConverter;
import com.example.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment {
    private NewsAdapter mRecyclerAdapter;
    private List<Article> mArticles = new ArrayList<>();
    private TextView mCountry;

    @Inject
    NewsViewModel mNewsViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((NewsApp) requireActivity().getApplication()).getAppComponent().inject(this);

        initWidgets(view);
        initRecyclerView(view);
        updateData();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(view.findViewById(R.id.toolbar));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initWidgets(View view){
        mCountry = view.findViewById(R.id.tv_country);
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.top_news_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerAdapter = new NewsAdapter(view.getContext(), mArticles);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void updateData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        String country = sp.getString("country", Constants.COUNTRY_US);

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(CountryAbbreviationConverter.getCountryFromAbbreviation(country));
        sb.append(")");

        mCountry.setText(sb.toString());

        mNewsViewModel.getTopNewsData(country).observe(getViewLifecycleOwner(), newsResponse -> {
            mArticles.clear();
            mArticles.addAll(newsResponse.getArticles());
            mRecyclerAdapter.notifyDataSetChanged();
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    //category
    //The category you want to get headlines for. Possible options: business entertainment
    // general health science sports technology .
    // Note: you can't mix this param with the sources param.
    //TODO: add category picker
}
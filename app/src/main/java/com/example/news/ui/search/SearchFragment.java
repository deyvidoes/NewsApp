package com.example.news.ui.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.NewsApp;
import com.example.news.R;
import com.example.news.adapters.NewsAdapter;
import com.example.news.models.Article;
import com.example.news.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mRecyclerAdapter;
    private List<Article> mArticles = new ArrayList<>();
    private Button mSearchBtn;
    private EditText mSearchText;

    @Inject
    NewsViewModel mNewsViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((NewsApp) requireActivity().getApplication()).getAppComponent().inject(this);

        initWidgets(view);
        initRecyclerView(view);
        setupListeners();
    }

    //TODO: Have recycler jump to top of the list after a search.
    //TODO: Have search word selected if clicked after searching.
    
    private void initWidgets(View view) {
        mSearchBtn = view.findViewById(R.id.btn_search);
        mSearchText = view.findViewById(R.id.et_search);
    }

    public void setupListeners() {
        mSearchBtn.setOnClickListener(view -> {
            updateDataIfPossible();
        });

        mSearchText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                updateDataIfPossible();
                return true;
            } else
                return false;
        });
    }

    private void updateDataIfPossible() {
        String text = mSearchText.getText().toString();
        if (text.isEmpty())
            Toast.makeText(getContext(), "Please enter a keyword to search", Toast.LENGTH_LONG).show();
        else
            updateData(text);
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.search_recycler);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerAdapter = new NewsAdapter(view.getContext(), mArticles);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void updateData(String word) {
        mNewsViewModel.getNewsByWordData(word).observe(getViewLifecycleOwner(), newsResponse -> {
            mArticles.clear();
            mArticles.addAll(newsResponse.getArticles());
            mRecyclerAdapter.notifyDataSetChanged();
            mLayoutManager.scrollToPositionWithOffset(0, 0);
            hideSoftKeyboard(requireActivity());
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
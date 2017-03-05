package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.darsh.jokeview.JokeViewActivity;

/**
 * Created by darshan on 6/3/17.
 */

public class MainActivityFragmentCommon extends Fragment implements JokeFetcherAsyncTask.JokeFetchedListener {
    protected TextView instructions;
    protected Button button;
    protected ProgressBar progressBar;

    private JokeFetcherAsyncTask asyncTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        instructions = (TextView) view.findViewById(R.id.instructions_text_view);
        button = (Button) view.findViewById(R.id.button_tell_joke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJoke();
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        Prior to start JokeViewActivity, the progressBar is visible while
        the instructions and button are not. On returning from JokeViewActivity,
        onStart is called. Check here if asyncTask is null (ie, app is launched
        again) or if asyncTask has finished executing. If either of those conditions
        are true, hide progressBar and show instructions and button.
         */
        if (asyncTask == null
                || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            updateUI(false);
        }
    }

    protected void getJoke() {
        updateUI(true);
        startJokeFetcherTask();
    }

    protected void updateUI(boolean isLoading) {
        if (isLoading) {
            instructions.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        } else {
            instructions.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    protected void startJokeFetcherTask() {
        asyncTask = new JokeFetcherAsyncTask(this);
        asyncTask.execute();
    }

    @Override
    public void onJokeFetched(String joke) {
        if (getActivity() == null) {
            return;
        }

        Intent intent = new Intent(getActivity(), JokeViewActivity.class);
        intent.putExtra(JokeViewActivity.JOKE_EXTRA, joke);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        button.setOnClickListener(null);
        asyncTask.cancel(true);
    }
}


package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.jokebackend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by darshan on 2/3/17.
 */

public class JokeFetcherAsyncTask extends AsyncTask<Void, Void, String> {
    private JokeApi jokeApi;

    private JokeFetchedListener jokeFetchedListener;

    JokeFetcherAsyncTask(JokeFetchedListener jokeFetchedListener) {
        this.jokeFetchedListener = jokeFetchedListener;
        this.jokeApi = null;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (jokeApi == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            jokeApi = builder.build();
        }


        String joke = null;
        try {
            joke = jokeApi.fetchJoke().execute().getJoke();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return joke;
    }

    @Override
    protected void onPostExecute(String joke) {
        if (jokeFetchedListener != null) {
            jokeFetchedListener.onJokeFetched(joke);
        }
    }

    public interface JokeFetchedListener {
        void onJokeFetched(String joke);
    }
}

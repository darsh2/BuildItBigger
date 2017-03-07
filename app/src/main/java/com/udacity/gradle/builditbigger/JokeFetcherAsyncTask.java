package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.jokebackend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;

import java.io.IOException;

/**
 * Created by darshan on 2/3/17.
 */

public class JokeFetcherAsyncTask extends AsyncTask<Void, Void, String> {
    /**
     * <p>On adding a new Endpoint and running a Gradle build thereafter,
     * the following classes are auto-generated:
     * <ul>
     *     <li>Api class which additionally has a Builder class and an ApiMethod class.</li>
     *     <li>ApiRequest</li>
     *     <li>ApiRequestInitializer</li>
     *     <li>ApiScope</li>
     *     <li>The response model class found in the model folder</li>
     * </ul>
     *
     * <p>The above class files can be found here -
     * "$(PATH_TO_GCE_MODULE)/build/classes/endpointsSrc/$(PACKAGE_NAME)/$(API_NAME)/",
     * while the source code of these class files are available in -
     * "$(PATH_TO_GCE_MODULE)/build/generated-source/endpoints/java/$(PACKAGE_NAME)/$(API_NAME)/"
     *
     * <p>The Api class has a inner class for each Api method with a unique
     * rest path. The ApiMethod class is a subclass of ApiRequest class which
     * in turn is a subclass of {@link com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest
     * AbstractGoogleJsonClientRequest}. Hence to make a request,
     * {@link AbstractGoogleJsonClientRequest#execute()} has to be called.
     *
     * <p>In this scenario, {@link JokeApi} is the class generated for the api defined
     * in JokeEndpoint. Calling {@link JokeApi#fetchJoke()} returns the AbstractGoogleClientRequest
     * object which then is executed to receive the joke.
     */
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
                    .setRootUrl(BuildConfig.GCE_ROOT_URL)
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

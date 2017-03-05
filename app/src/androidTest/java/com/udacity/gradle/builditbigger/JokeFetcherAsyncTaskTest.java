package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

/**
 * Created by darshan on 5/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class JokeFetcherAsyncTaskTest {
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void fetchJokeTest() {
        new JokeFetcherAsyncTask(new JokeFetchedListener()).execute();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class JokeFetchedListener implements JokeFetcherAsyncTask.JokeFetchedListener {
        @Override
        public void onJokeFetched(String joke) {
            assertTrue(joke != null);
            countDownLatch.countDown();
        }
    }
}

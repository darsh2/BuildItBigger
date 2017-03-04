package com.example.jokebackend;

import com.example.JokeProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "jokebackend.example.com",
                ownerName = "jokebackend.example.com",
                packagePath = ""
        )
)
public class JokeEndpoint {
    /**
     * Android studio automatically generates a getJoke and insertJoke
     * ApiMethod on 'Generating cloud endpoint from Java class'. On
     * adding fetchJoke(), received an error saying multiple endpoints
     * with the same REST paths. Add path param to @ApiMethod annotation
     * to resolve it. See here - http://stackoverflow.com/a/17005351
     *
     * While this is not necessary currently, just included it for
     * future reference.
     */
    @ApiMethod(name = "fetchJoke", path = "fetch_joke")
    public Joke fetchJoke() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Joke joke = new Joke();
        joke.setJoke(new JokeProvider().getJoke());
        return joke;
    }
}
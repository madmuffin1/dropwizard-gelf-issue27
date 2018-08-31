package com.github.madmuffin1;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class ApplicationWithGelfLoggingWorks {

    @ClassRule
    public static final DropwizardAppRule<SampleConfiguration> APP_RULE = new DropwizardAppRule<>(RequestLoggingApplication.class, ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void blockingWorks() throws ExecutionException, InterruptedException {
        Response response = Clients.buildClient("blocking-works", APP_RULE.getEnvironment()).target(
                String.format("http://localhost:%d/test/blocking", APP_RULE.getLocalPort()))
                .request()
                .get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void asyncWorks() throws ExecutionException, InterruptedException {
        Response response = Clients.buildClient("async-works", APP_RULE.getEnvironment()).target(
                String.format("http://localhost:%d/test/async", APP_RULE.getLocalPort()))
                .request()
                .get();

        assertEquals(200, response.getStatus());
    }

}

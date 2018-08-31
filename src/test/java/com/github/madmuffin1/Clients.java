package com.github.madmuffin1;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

public class Clients {

    private static final Logger LOGGER = LoggerFactory.getLogger(Clients.class);

    public static Client buildClient(String name, Environment environment) {

        HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();
        httpClientConfiguration.setTimeout(Duration.milliseconds(120000));

        HttpClientBuilder clientBuilder = new HttpClientBuilder(environment);
        clientBuilder.using(httpClientConfiguration);


        final JerseyClientBuilder jerseyClientBuilder = new JerseyClientBuilder(environment);
        jerseyClientBuilder.setApacheHttpClientBuilder(clientBuilder);
        Client client;
        client = jerseyClientBuilder.build(name);
        client.property(ClientProperties.READ_TIMEOUT, 120000);
        return client;
    }

}

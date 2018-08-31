package com.github.madmuffin1;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WorkingApplication extends Application<SampleConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WorkingApplication().run(args);
    }

    @Override
    public String getName() {
        return "sample";
    }

    @Override
    public void initialize(final Bootstrap<SampleConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final SampleConfiguration configuration,
                    final Environment environment) {

        environment.jersey().register(TestResource.class);

    }

}

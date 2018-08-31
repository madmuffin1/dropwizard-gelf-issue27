package com.github.madmuffin1;

import io.dropwizard.setup.Environment;
import net.gini.dropwizard.gelf.filters.GelfLoggingFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class RequestLoggingApplication extends WorkingApplication {

    public static void main(final String[] args) throws Exception {
        new RequestLoggingApplication().run(args);
    }

    @Override
    public void run(final SampleConfiguration configuration,
                    final Environment environment) {

        super.run(configuration, environment);

        environment.servlets()
                .addFilter("request-logs", new GelfLoggingFilter())
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");




    }

}

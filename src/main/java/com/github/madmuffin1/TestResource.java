package com.github.madmuffin1;

import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;

@Path("/test")
public class TestResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestResource.class);

    @GET
    @Path("/async")
    public void testAsync(@Suspended final AsyncResponse asyncResponse,
                     @Context HttpServletRequest servletRequest) throws IOException {

        // set a timeout, so the request won't run forever
        servletRequest.getAsyncContext().setTimeout(5000L);

        final AsyncContext asyncContext = servletRequest.getAsyncContext();
        final ServletOutputStream servletOutputStream = asyncContext.getResponse().getOutputStream();


        final ArrayDeque<String> queue = Queues.newArrayDeque(Arrays.asList("datagram1", "datagram2"));

        servletOutputStream.setWriteListener(new WriteListener() {
            private boolean done;

            @Override
            public void onWritePossible() throws IOException {
                while (servletOutputStream.isReady()) {
                    if(done) {
                        asyncContext.complete();
                        break;
                    } else {
                        // here you would usually get the data from some async source, e.g. a Subscriber
                        if(queue.isEmpty()) {
                            done = true;
                        } else {
                            servletOutputStream.write(queue.pop().getBytes());
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("Error", throwable);
            }
        });
    }

    @GET
    @Path("/blocking")
    public Response testBlocking() {
        return Response.ok().build();
    }

}

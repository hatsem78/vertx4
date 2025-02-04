package com.octavio.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponse {



    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Response());
        vertx.deployVerticle(new Request());
    }

    static class Request extends AbstractVerticle {

        public static final String ADDRESS = "my.request.address";
        private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            var enventBus = vertx.eventBus();
            String message = "hello world!!";
            LOGGER.debug("Sending message: {}", message);

            enventBus.<String>request(ADDRESS, message, reply->{
                LOGGER.info("response: {}", reply.result().body());
            });
        }
    }

    static class Response extends AbstractVerticle {

        private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().consumer(Request.ADDRESS, message -> {
               LOGGER.debug("Received message: {}", message.body());
               message.reply("Received your message! Thank");
            });
        }

    }
}

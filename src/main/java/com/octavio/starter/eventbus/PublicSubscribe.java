package com.octavio.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublicSubscribe {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Publish());
        vertx.deployVerticle(new Subscribe());
        vertx.deployVerticle(new Subscribe1());

    }

    public static class Publish extends AbstractVerticle {

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.setPeriodic(Duration.ofSeconds(10).toMillis(),id -> {
                vertx.eventBus().publish(Publish.class.getName(), "A message for everybody");
            });

        }
    }

    public static class Subscribe extends AbstractVerticle {

        private static final Logger LOGGER = LoggerFactory.getLogger(Subscribe.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().consumer(Publish.class.getName(), message -> {
                LOGGER.debug("Subscribe Received message for publish");
            });
        }
    }

    public static class Subscribe1 extends AbstractVerticle {

        private static final Logger LOGGER = LoggerFactory.getLogger(Subscribe1.class);

        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().consumer(Publish.class.getName(), message -> {
                LOGGER.debug("Subscribe1 Received message for publish");
            });
        }
    }
}

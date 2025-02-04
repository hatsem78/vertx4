package com.octavio.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPoint {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Sender());
        vertx.deployVerticle(new Receiver());
    }

    static class Sender extends AbstractVerticle {
        private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.setPeriodic(1000, id->{
                vertx.eventBus().<String>send(Sender.class.getName(), "Sending message hello");
                LOGGER.debug("id: {}", id.toString());
            });

        }
    }

    static class Receiver extends AbstractVerticle {
        private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
        @Override
        public void start(Promise<Void> startPromise) throws Exception {
            startPromise.complete();
            vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
                LOGGER.debug("Received message: {}", message.body());
                message.reply("Sending thank for message");
            });
        }
    }
}

package com.octavio.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Worker());
    }

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(new WorkerVerticle(),
          new DeploymentOptions()
                  .setWorkerPoolSize(1)
                  .setWorkerPoolName("my-worker-verticle")
        );
        LOGGER.info("executeBlocking");
        startPromise.complete();
        vertx.executeBlocking(event -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOGGER.error("Error executeBlocking {}", e.getMessage());
                event.fail(e.getMessage());
            }
            event.complete();
        }, asyncResult -> {
            if (asyncResult.succeeded()) {
                LOGGER.info("Blocking done");
            } else {
                LOGGER.error("Blocking error: {}", asyncResult.cause().getMessage());
            }
        });
    }
}

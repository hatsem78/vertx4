package com.octavio.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        LOG.info("Deployed worker verticle");
        startPromise.complete();
        Thread.sleep(5000);
        LOG.debug("Blocking operation done");
    }
}

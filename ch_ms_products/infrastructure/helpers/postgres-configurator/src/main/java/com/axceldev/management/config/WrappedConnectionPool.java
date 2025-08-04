package com.axceldev.management.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import reactor.core.publisher.Mono;

public class WrappedConnectionPool extends ConnectionPool {

    private ConnectionPool pool;

    public WrappedConnectionPool(ConnectionPoolConfiguration configuration) {
        super(configuration);
        refresh(configuration);
    }

    public void refresh(ConnectionPoolConfiguration poolConfiguration) {
        this.pool = new ConnectionPool(poolConfiguration);
    }

    @Override
    public Mono<Void> close() {
        return pool.close();
    }

    @Override
    public Mono<Connection> create() {
        return pool.create();
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return pool.getMetadata();
    }

    @Override
    public ConnectionFactory unwrap() {
        return pool.unwrap();
    }

    @Override
    public void dispose() {
        pool.dispose();
    }
}

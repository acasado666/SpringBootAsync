package io.git.async.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@EnableAsync
@Slf4j
public class AsyncThreadPoolConfig {

    @Value("${casado.async.microservice.corepoolsize}")
    private int corePoolSize;

    @Value("${casado.async.microservice.maxpoolsize}")
    private int maxPoolSize;


    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor()  {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

    //Also we can use ExecutorService
    @Bean(name = "asyncTaskExecutor")
    public ExecutorService asyncTaskExecutor() {
        log.info("Creating Async Task Executor for Service API to serve request in parallel threads");
        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
}

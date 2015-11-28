package com.cabonline.musicdb.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by s_lor_000 on 11/28/2015.
 */
@Configuration
@EnableAsync
public class ExecutorConfiguration {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(
            @Value("${app.executor.corepoolsize:10}") int corePoolSize,
            @Value("${app.executor.maxpoolsize:20}") int maxPoolSize,
            @Value("${app.executor.queuecapacity:50}") int queueCapacity){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        return threadPoolTaskExecutor;
    }

}

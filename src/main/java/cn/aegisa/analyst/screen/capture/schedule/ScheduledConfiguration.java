package cn.aegisa.analyst.screen.capture.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 9/13/2018 10:47 AM
 */
@Configuration
public class ScheduledConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService setTaskExecutors() {
        return Executors.newScheduledThreadPool(10);
    }

}

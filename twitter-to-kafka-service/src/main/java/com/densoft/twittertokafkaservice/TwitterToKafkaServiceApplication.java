package com.densoft.twittertokafkaservice;


import com.densoft.twittertokafkaservice.init.StreamInitializer;
import com.densoft.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {
        "com.densoft.appconfigdata",
        "com.densoft.twittertokafkaservice",
        "com.densoft.kafka.producer",
        "com.densoft.kafka.admin",
        "com.densoft.retryConfig",
})
@RequiredArgsConstructor
@Slf4j
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

  private final StreamInitializer streamInitializer;
    private final StreamRunner streamRunner;
    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("App Starts...");
        streamInitializer.init();
        streamRunner.start();
    }
}

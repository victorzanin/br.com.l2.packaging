package br.com.l2.packaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.l2")
@ConfigurationPropertiesScan("br.com.experian")
@EnableConfigurationProperties
public class PackagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PackagingApplication.class, args);
    }

}

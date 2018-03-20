package uk.co.blackcell.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Shared email service main application.
 *
 * @James Bishop
 */
@SpringBootApplication
@ComponentScan({ "hodge.co.uk" })
@EnableJpaRepositories("hodge.co.uk.dao")
@EntityScan("hodge.co.uk.entity")
@EnableAspectJAutoProxy
public class SharedEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedEmailApplication.class, args);
    }

}

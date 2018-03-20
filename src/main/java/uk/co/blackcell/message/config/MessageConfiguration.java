package uk.co.blackcell.message.config;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

/**
 * Configuration class that encapsulates all JMS and Queueing related configuration and bean creation.
 *
 * @author James Bishop
 */
@Slf4j
@Configuration
@EnableJms
public class MessageConfiguration {

    /**
     * Get a handle to the default set of AWS credentials used for accessing the AWS SQS Queues
     * @return The default AWSCredentialsProvider instance
     */
    @Bean
    public AWSCredentialsProvider credentialsProviderBean() {
        return new DefaultAWSCredentialsProviderChain();
    }

    /**
     * Get a handle to the JMS Connection Factory used to create queue connections, sessions, etc.
     * @param awsCredentialsProvider An instance of AWS Credentials used to authenticate to AWS queue resources
     * @return A ConnectFactory instance
     */
    @Bean
    @DependsOn("credentialsProviderBean")
    public ConnectionFactory connectionFactory(AWSCredentialsProvider awsCredentialsProvider) {
        return SQSConnectionFactory.builder()
                .withRegion(Region.getRegion(Regions.EU_WEST_2))
                .withAWSCredentialsProvider(awsCredentialsProvider)
                .build();
    }

    /**
     * For the description of this class please see the standard Spring java Doc API
     * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/config/DefaultJmsListenerContainerFactory.html
     *
     * @param connectionFactory is the {@link ConnectionFactory}
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    @DependsOn("connectionFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(final ConnectionFactory connectionFactory, final DefaultErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setErrorHandler(errorHandler);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return factory;
    }

    @Bean
    @DependsOn("connectionFactory")
    public JmsTemplate defaultJmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Service
    public class DefaultErrorHandler implements ErrorHandler {

        @Override
        public void handleError(Throwable throwable) {
            log.error("*** JMS Error (Will failover to associated DLQ after 3 attempts): ", throwable);
        }

    }
}

package uk.co.blackcell.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Spring configuration class for Shared Email Service.
 *
 * @author James Bishop
 *
 */
@Configuration
@Slf4j
public class SharedEmailConfiguration {

    @Autowired
    private TemplateLoader templateLoader;

    /**
     * Create a singleton Spring bean Freemarker Configration instance which is then utilised
     * by the framework to perform processing and subsitution.
     *
     * NB. This is a heavyweight operation with File I/O which should only occur once, hence the
     * bean creation. Following the creation of the Configuration, the free marker templates are cached.
     *
     * @return a Freemarker Configuration instance
     */
    @Bean
    public freemarker.template.Configuration configuration() throws URISyntaxException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_25);

        cfg.setLocale(Locale.UK);
        cfg.setTemplateLoader(templateLoader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        return cfg;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_2).withCredentials(new DefaultAWSCredentialsProviderChain()).build();
    }

    @Bean
    public RestTemplateBuilder createRestTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}

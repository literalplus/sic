package li.l1t.sic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * The Spring starter class for sic.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-01-15
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SicStarter {
    public static void main(String[] args) {
        SpringApplication.run(SicStarter.class, args).getEnvironment();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setJsonPrefix(")]}',\n"); //Prefix JSON to prevent it from being executed - prevents hackers from doing <script src="/api/stuff">
        return converter; //Angular strips this prefix
    }
}

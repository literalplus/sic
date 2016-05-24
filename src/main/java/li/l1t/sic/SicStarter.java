package li.l1t.sic;

import com.codahale.metrics.MetricRegistry;
import li.l1t.sic.config.SicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.reader.MetricRegistryMetricReader;
import org.springframework.boot.actuate.metrics.statsd.StatsdMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jmx.export.MBeanExporter;

/**
 * The Spring starter class for sic.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-01-15
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SicStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SicStarter.class);
    @Autowired
    private SicConfiguration configuration;
    @Autowired
    private MetricRegistry metricRegistry;

    public static void main(String[] args) {
        SpringApplication.run(SicStarter.class, args).getEnvironment();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setJsonPrefix(")]}',\n"); //Prefix JSON to prevent it from being executed - prevents hackers from doing <script src="/api/stuff">
        return converter; //Angular strips this prefix
    }

    @Bean
    @ExportMetricReader
    MetricReader metricReader() {
        return new MetricRegistryMetricReader(metricRegistry);
    }

    @Bean
    @ExportMetricWriter
    MetricWriter metricWriter() {
        if(configuration.isStatsdEnabled()) {
            LOGGER.info("Statsd connection: {}:{}", configuration.getStatsdHost(), configuration.getStatsdPort());
            return new StatsdMetricWriter("sic",
                    configuration.getStatsdHost(),
                    configuration.getStatsdPort()
            );
        } else {
            LOGGER.info("Not using Statsd.");
            return new JmxMetricWriter(new MBeanExporter()); //don't know what else to do
        }
    }
}

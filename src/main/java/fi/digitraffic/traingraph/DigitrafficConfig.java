package fi.digitraffic.traingraph;

import fi.digitraffic.traingraph.client.ApiClient;
import fi.digitraffic.traingraph.api.TrainsApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DigitrafficConfig {

    @Bean
    public TrainsApi trainsApi() {
        ApiClient client = new ApiClient();
        client.setBasePath("https://rata.digitraffic.fi/api/v1");
        return new TrainsApi(client);
    }
}

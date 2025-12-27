package fi.digitraffic.traingraph;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class TraingraphApplication {

	public static void main(String[] args) {
	  SpringApplication.run(TraingraphApplication.class, args);
    ExchangeStrategies strategies = ExchangeStrategies
      .builder()
      .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16 MB
      .build();
      
    WebClient client = WebClient.builder()
      .baseUrl("https://rata.digitraffic.fi")
			.exchangeStrategies(strategies)
      .defaultHeader("Digitraffic-User", "Nimimerkki/EsimerkkiApp 0.1")
      .defaultHeader("Accept", "*/*")
      .build(); 
      
      String trains = client.get()
                .uri("/api/v1/trains")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // blocks like curl
				
      
	  System.out.println(trains);

	}


}

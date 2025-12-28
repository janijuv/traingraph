package fi.digitraffic.traingraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class TraingraphApplication {

	public static void main(String[] args) throws FileNotFoundException {
	  SpringApplication.run(TraingraphApplication.class, args);
    ExchangeStrategies strategies = ExchangeStrategies
      .builder()
      .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16 MB
      .build();
      
    WebClient client = WebClient.builder()
      .baseUrl("https://rata.digitraffic.fi/api/v1")
			.exchangeStrategies(strategies)
      .defaultHeader("Digitraffic-User", "Nimimerkki/EsimerkkiApp 0.1")
      .defaultHeader("Accept", "*/*")
      // .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
      .build(); 
      
      String liveTrains = client.get()
                .uri("/live-trains/station/PSL/LPV?departure_date=2025-12-28")
                .retrieve()
                //.exchangeToMono(response -> {
                //  System.out.println("Status: " + response.statusCode());
                //  return response.bodyToMono(String.class);
                // })
                .bodyToMono(String.class)
                .block(); // blocks like curl

      try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(liveTrains);
        String prettyPrintString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

      
      File file = new File("output.txt");
      PrintStream stream = new PrintStream(file);
      PrintStream console = System.out;
      System.setOut(stream);

      System.out.println(prettyPrintString);
      System.setOut(console);


      if (jsonNode.isArray()) {
        for (JsonNode node : jsonNode) {
          System.out.println("Train number: " + node.get("trainNumber").asText());
          System.out.println("Departure date: " + node.get("departureDate").asInt());
        }
      }


    } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      


	}


}

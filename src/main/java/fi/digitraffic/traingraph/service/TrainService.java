package fi.digitraffic.traingraph.service;

import fi.digitraffic.traingraph.api.TrainsApi;
import fi.digitraffic.traingraph.exception.DigitrafficException;
import fi.digitraffic.traingraph.model.Train;
import fi.digitraffic.traingraph.view.TrainView;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TrainService {

    private final TrainsApi trainsApi;

    public TrainService(TrainsApi trainsApi) {
        this.trainsApi = trainsApi;
    }

    public List<TrainView> getLiveTrains(String departureDate) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate date = LocalDate.parse("2005-12-12", formatter);
      List<Train> trains = new ArrayList<Train>();
      try {
        trains = trainsApi.getTrainsByDepartureDate(date, true);
      } catch (Exception e) {
        throw new DigitrafficException(
                    "Junatietojen haku epäonnistui", e);
      }

      return trains.stream()
                .map(t -> new TrainView(
                        t.getTrainNumber(),
                        t.getTrainType(),
                        t.getOperatorShortCode()
                ))
                .toList();
            } 
        }  

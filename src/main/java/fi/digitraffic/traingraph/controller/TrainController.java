package fi.digitraffic.traingraph.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.digitraffic.traingraph.model.Train;
import fi.digitraffic.traingraph.service.TrainService;
import fi.digitraffic.traingraph.view.TrainView;

@Controller
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/trains")
    public String trains(Model model) {
        List<TrainView> trains = trainService.getLiveTrains("2025-01-10");
        model.addAttribute("trains", trains);
        return "trains";
    }
}

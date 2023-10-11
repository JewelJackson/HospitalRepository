package com.hospital.hospitalmanagementsystem.Controller;

import com.hospital.hospitalmanagementsystem.Response.CovidContactResponse;
import com.hospital.hospitalmanagementsystem.Response.CovidDataResponse;
import com.hospital.hospitalmanagementsystem.Response.CovidHospitalBedResponse;
import com.hospital.hospitalmanagementsystem.Service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/covid")
public class CovidController {

    @Autowired
    private CovidService covidService;

    @GetMapping("/data/{loc}")
    @ResponseBody
    public Mono<CovidDataResponse.Data.RegionalData> getCovidDataByLocation(@PathVariable("loc") String loc) {
        return covidService.getCovidDataByLocation(loc);
    }

    @GetMapping("/contact/{loc}")
    @ResponseBody
    public Mono<CovidContactResponse.Data.Contacts.Regional> getHelplineNumber(@PathVariable("loc") String loc) {
        return covidService.getCovidContactHelpline(loc);
    }

    @GetMapping("/hospital/bed/{state}")
    @ResponseBody
    public Mono<CovidHospitalBedResponse.Data.Regional> getBedDetailsByState(@PathVariable("state") String state) {
        return covidService.getCovidBedDataByState(state);
    }
}

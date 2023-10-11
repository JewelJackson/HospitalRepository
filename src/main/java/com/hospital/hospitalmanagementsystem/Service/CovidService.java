package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Handler.InvalidLocationException;
import com.hospital.hospitalmanagementsystem.Response.CovidContactResponse;
import com.hospital.hospitalmanagementsystem.Response.CovidDataResponse;
import com.hospital.hospitalmanagementsystem.Response.CovidHospitalBedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CovidService {

    /*@Value("${covidservice.api.base-url}")
    private String baseUrl;*/

    private final WebClient webClient;

    public CovidService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.rootnet.in/covid19-in")
                .build();
    }

    /**
     * Covid-19 Case counts
     * @param loc
     * @return
     */
    public Mono<CovidDataResponse.Data.RegionalData> getCovidDataByLocation(String loc) {
        return webClient
                .get()
                .uri("/stats/latest")
                .retrieve()
                .bodyToMono(CovidDataResponse.class)
                .flatMap(covidData -> filterDataByLocation(covidData, loc))
                .switchIfEmpty(Mono.error(new InvalidLocationException("Location not present")));
    }

    private Mono<CovidDataResponse.Data.RegionalData> filterDataByLocation(CovidDataResponse covidResponse, String loc) {
        return Mono.justOrEmpty(covidResponse.getData().getRegional().stream()
                .filter(data -> data.getLoc().equalsIgnoreCase(loc))
                .findFirst());
    }

    /**
     * Covid-19 Helpline Numbers
     * @param loc
     * @return
     */
    public Mono<CovidContactResponse.Data.Contacts.Regional> getCovidContactHelpline(String loc){
        return webClient
                .get()
                .uri("/contacts")
                .retrieve()
                .bodyToMono(CovidContactResponse.class)
                .flatMap(covidContact -> filterContactByLocation(covidContact,loc))
                .switchIfEmpty(Mono.error(new InvalidLocationException("Location not found.")));
    }

    private Mono<CovidContactResponse.Data.Contacts.Regional> filterContactByLocation(CovidContactResponse contactResponse, String loc){
        return Mono.justOrEmpty(contactResponse.getData().getContacts().getRegional().stream()
                .filter(contact -> contact.getLoc().equalsIgnoreCase(loc))
                .findFirst());
    }


    /**
     * Covid-19 Bed Details
     * @param state
     * @return
     */
    public Mono<CovidHospitalBedResponse.Data.Regional> getCovidBedDataByState(String state) {
        return webClient
                .get()
                .uri("/hospitals/beds")
                .retrieve()
                .bodyToMono(CovidHospitalBedResponse.class)
                .flatMap(bedData -> filterDataByState(bedData, state))
                .switchIfEmpty(Mono.error(new InvalidLocationException("State not present")));
    }

    private Mono<CovidHospitalBedResponse.Data.Regional> filterDataByState(CovidHospitalBedResponse bedResponse, String state) {
        return Mono.justOrEmpty(bedResponse.getData().getRegional().stream()
                .filter(bed -> bed.getState().equalsIgnoreCase(state))
                .findFirst());
    }
}







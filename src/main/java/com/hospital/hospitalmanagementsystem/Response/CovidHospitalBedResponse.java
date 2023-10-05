package com.hospital.hospitalmanagementsystem.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CovidHospitalBedResponse {
    private boolean success;
    private Data data;
    private String lastRefreshed;
    private String lastOriginUpdate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private Summary summary;
        private List<Sources> sources;
        private List<Regional> regional;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Summary{

            private int ruralHospitals;
            private int ruralBeds;
            private int urbanHospitals;
            private int urbanBeds;
            private int totalHospitals;
            private int totalBeds;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Sources{

            private String url;
            private String lastUpdated;
    }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Regional{

            private String state;
            private int ruralHospitals;
            private int ruralBeds;
            private int urbanHospitals;
            private int urbanBeds;
            private int totalHospitals;
            private int totalBeds;
        }
    }
}

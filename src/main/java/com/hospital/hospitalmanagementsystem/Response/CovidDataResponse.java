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
public class CovidDataResponse {

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
        private List<RegionalData> regional;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Summary {
            private int total;
            private int confirmedCasesIndian;
            private int confirmedCasesForeign;
            private int discharged;
            private int deaths;
            private int confirmedButLocationUnidentified;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RegionalData {
            private String loc;
            private int confirmedCasesIndian;
            private int confirmedCasesForeign;
            private int discharged;
            private int deaths;
            private int totalConfirmed;
        }
    }
}
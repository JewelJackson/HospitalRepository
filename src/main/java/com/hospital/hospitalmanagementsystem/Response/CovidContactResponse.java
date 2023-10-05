package com.hospital.hospitalmanagementsystem.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CovidContactResponse {
    private boolean success;
    private Data data;
    private String lastRefreshed;
    private String lastOriginUpdate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private Contacts contacts;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Contacts {
            private Primary primary;
            private List<Regional> regional;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Primary {
                private String number;
                private int number_tollfree;
                private String email;
                private String twitter;
                private String facebook;
                private List<String> media;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Regional{

                private String loc;
                private String number;
            }
        }
    }
}

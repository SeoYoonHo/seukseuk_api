package com.lsyh.seukseuk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoDTO {
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoRequest {
        private Intent intent;
        private UserRequest userRequest;
        private Bot bot;
        private Action action;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Intent {
            private String id;
            private String name;
            private Extra extra;
            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Extra {
                private Reason reason;
                private Knowledge knowledge;

                @Data
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Reason {
                    private int code;
                    private String message;
                }

                @Data
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Knowledge {
                    private String responseType;
                    private List<MatchedKnowledge> matchedKnowledges;

                    @Data
                    @NoArgsConstructor
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class MatchedKnowledge {
                        private List<String> categories;
                        private String question;
                        private String answer;
                        private String imageUrl;
                        private String landingUrl;
                    }
                }
            }
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class UserRequest {
            private String timezone;
            private Params params;
            private Block block;
            private String utterance;
            private String lang;
            private User user;

            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Params {
                private String surface;
                private String ignoreMe;
            }

            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Block {
                private String id;
                private String name;
            }

            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class User {
                private String id;
                private String type;
                private Properties properties;

                @Data
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Properties {}
            }
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Bot {
            private String id;
            private String name;
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Action {
            private String name;
            private Object clientExtra;
            private Map<String, String> params;
            private String id;
            private Map<String, DetailParam> detailParams;

            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class DetailParam {
                private String origin;
                private String value;
                private boolean group;
            }
        }
    }
}

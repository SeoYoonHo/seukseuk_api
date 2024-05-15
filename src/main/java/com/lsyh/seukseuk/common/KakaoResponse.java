package com.lsyh.seukseuk.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값이 아닌 필드만 JSON에 포함
public class KakaoResponse {
    private String version = "2.0";
    private Template template;
    private Context context;
    private Map<String, Object> data = new HashMap<>();

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Template {
        private List<Output> outputs = new ArrayList<>();
        private List<QuickReply> quickReplies = new ArrayList<>();

        @Data
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Output {
            private SimpleText simpleText;
            private SimpleImage simpleImage;
            // 다른 출력 타입에 대한 정의가 필요하면 여기에 추가

            @Data
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class SimpleText {
                private String text;
            }

            @Data
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class SimpleImage {
                private String imageUrl;
                private String altText;
            }
        }

        @Data
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class QuickReply {
            private String label;
            private String action;
            private String messageText;
            // 필요한 경우 다른 속성 추가
        }
    }

    @Data
    @NoArgsConstructor
    public static class Context {
        private Map<String, Object> values;
    }
}
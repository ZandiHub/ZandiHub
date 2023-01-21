package com.zandihub.zandihub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Data
public class GitHubAccessTokenDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String access_token;
        private String scope;
        private String token_type;
    }

    @Data
    @AllArgsConstructor
    public static class Request {

        public MultiValueMap<String, String> getFormData(String code, String client_id, String client_secret) {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", client_id);
            formData.add("client_secret", client_secret);
            formData.add("code", code);
            return formData;
        }

    }

}

package com.zandihub.zandihub.controller;

import com.zandihub.zandihub.dto.GitHubAccessTokenDto;
import com.zandihub.zandihub.dto.GitHubAccessTokenDto.Request;
import com.zandihub.zandihub.dto.GitHubUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthController {
    GitHubAccessTokenDto.Request request = new Request();
    WebClient webClient = WebClient.create("http://localhost:8080");
    String accessTokenUri = "https://github.com/login/oauth/access_token";
    String userInfoUri = "https://api.github.com/user";
    @Value("${github.client-id}")
    private String client_id;
    @Value("${github.client-secret}")
    private String client_secret;

    @GetMapping("/login")
    public String authHome() {
        return "login";
    }

    @GetMapping("/github_login")
    public String exRedirect1() {
        String url = "https://github.com/login/oauth/authorize?client_id="+client_id+"&redirect_uri=http://localhost:8080/auth/callback";
        return "redirect:"+url;
    }

    @GetMapping("/auth/callback")
    public String getUserInfo(@RequestParam String code) {
        String accessToken = getAccessToken(code);
        String userInfo = getLoginId(accessToken);
        System.out.println(userInfo);
        return "home";
    }

    private String getAccessToken(String code) {
        MultiValueMap<String, String> formData = request.getFormData(code,client_id,client_secret);
        System.out.println(formData);

        GitHubAccessTokenDto.Response gitHubAccessToken = webClient.post()
                .uri(accessTokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
//        .bodyValue(
//            Request.getBody(code)
//        )
                .retrieve()
                .bodyToMono(GitHubAccessTokenDto.Response.class)
                .block();
        return gitHubAccessToken.getAccess_token();
    }

    public String getLoginId(String accessToken) {
        GitHubUserInfoDto userInfo = webClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GitHubUserInfoDto.class)
                .block();
        return userInfo.getLogin();
    }

}



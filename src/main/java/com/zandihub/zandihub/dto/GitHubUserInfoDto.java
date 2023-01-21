package com.zandihub.zandihub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitHubUserInfoDto {
    private String name;
    private String login;
    private String id;
}


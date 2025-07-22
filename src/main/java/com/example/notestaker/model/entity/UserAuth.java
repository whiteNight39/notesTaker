package com.example.notestaker.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    private Integer authId;
    private Integer authUserId;
    private String authJwtToken;

    private Date authIssuedAt;
    private Date authExpiresAt;

    private Boolean authIsValid = true;

    private String authUserDeviceIp;
    private String authUserDeviceAgent;
}

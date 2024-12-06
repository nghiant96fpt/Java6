package com.fpoly.java6.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TwilioResponse {
    private String token;
    private String channelId;
}

package com.fpoly.java6.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TwilioCreateChannelNewUserBean {
    private String userIdOne;
    private String nameOne;
    private String userIdTwo;
    private String nameTwo;
}

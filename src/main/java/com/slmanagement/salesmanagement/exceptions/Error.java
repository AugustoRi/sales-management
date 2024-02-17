package com.slmanagement.salesmanagement.exceptions;

import lombok.Getter;

@Getter
public class Error {
    private String msgToUser;
    private String msgToDeveloper;

    public Error(String msgToUser, String msgToDeveloper) {
        this.msgToUser = msgToUser;
        this.msgToDeveloper = msgToDeveloper;
    }
}

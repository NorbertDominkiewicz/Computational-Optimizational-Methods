package com.ndominkiewicz.frontend.exception;

public class ServerNotConnected extends RuntimeException {
    public ServerNotConnected(String message) {
        super(message);
    }
}

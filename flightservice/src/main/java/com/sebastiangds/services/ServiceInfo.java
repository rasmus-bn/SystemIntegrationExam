package com.sebastiangds.services;

public class ServiceInfo {
    private String host;
    private int port;
    private String userName;
    private String password;

    public ServiceInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ServiceInfo(String host, int port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

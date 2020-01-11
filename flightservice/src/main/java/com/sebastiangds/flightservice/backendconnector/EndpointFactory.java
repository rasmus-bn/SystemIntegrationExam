package com.sebastiangds.flightservice.backendconnector;

import contract.interfaces.BeanInterface;
import logging.Sender;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class EndpointFactory {

    private final String ENV_VAL_KEY = "FLIGHT_SERVICE_PROD";

    private final String URL_PKG_PREFIXES = "org.jboss.ejb.client.naming";
    private final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private final String PROVIDER_URL = "http-remoting://35.207.154.57:8082";
    //private final String PROVIDER_URL = "http-remoting://localhost:8080";
    private final String LOOKUP_NAME = "ejb:/4/ContractBean!contract.interfaces.BeanInterface";

    public BeanInterface getEndpoint() throws IOException, TimeoutException {
        Sender sender = new Sender();
        EnvType envType = EnvType.DEV;
        Map<String, String> sysEnv = System.getenv();

        if (sysEnv.containsKey(this.ENV_VAL_KEY)) {
            String envVar = sysEnv.get(this.ENV_VAL_KEY);
            try {
                envType = EnvType.valueOf(envVar);
            } catch (IllegalArgumentException e) {
                String warningMsg =
                        "Environment " + envVar + " provided by "
                                + this.ENV_VAL_KEY + " not recognised. " +
                                "Continue in " + envType.toString();
                sender.makeLog("EndpointFactory", Level.SEVERE,"Bean connection error",warningMsg);
            }
        }

        switch (envType) {
            case DEV:
                return this.getDevEndpoint();
            case TEST:
                return this.getTestEndpoint();
            case PROD:
                return this.getProductionEndpoint();
            default:
                return this.getDevEndpoint();
        }
    }

    private BeanInterface getDevEndpoint() throws IOException, TimeoutException {
        return this.getProductionEndpoint();
    }

    private BeanInterface getTestEndpoint() {
        return null;
    }

    private BeanInterface getProductionEndpoint() throws IOException, TimeoutException {
        Sender sender = new Sender();
        System.out.println("Prod endpoint ...");
        try {
            Properties prop = new Properties();
            prop.put(Context.URL_PKG_PREFIXES, this.URL_PKG_PREFIXES);
            prop.put(Context.INITIAL_CONTEXT_FACTORY, this.INITIAL_CONTEXT_FACTORY);
            prop.put(Context.PROVIDER_URL, this.PROVIDER_URL);
            prop.put("jboss.naming.client.ejb.context", false);

            InitialContext ic = new InitialContext(prop);

            BeanInterface endpoint = (BeanInterface) ic.lookup(this.LOOKUP_NAME);
            return endpoint;
        } catch (NamingException e) {

            sender.makeLog("EndpointFactory", Level.SEVERE, "Bean naming wrong in production", e.getMessage());
            return null;
        }

    }}
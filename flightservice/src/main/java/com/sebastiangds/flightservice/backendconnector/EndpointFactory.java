package com.sebastiangds.flightservice.backendconnector;

import com.sebastiangds.services.EnvHelper;
import com.sebastiangds.services.EnvType;
import contract.interfaces.BeanInterface;
import logging.SILevel;
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
    private EnvType envType = EnvType.DEV;

    public BeanInterface getEndpoint() throws IOException, TimeoutException {
        envType = new EnvHelper().getEnvType();

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
            sender.makeLog(
                    EndpointFactory.class.getName(),
                    SILevel.INFO,
                    "Flight service connected",
                    "Connected to end point for env " + this.envType.toString());
            return endpoint;
        } catch (NamingException e) {

            sender.makeLog(EndpointFactory.class.getName(), SILevel.SEVERE, "Bean naming wrong in production", e.getMessage());
            return null;
        }

    }}
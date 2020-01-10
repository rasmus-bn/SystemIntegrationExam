package com.sebastiangds.flightservice.backendconnector;

import contract.interfaces.BeanInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Map;
import java.util.Properties;

public class EndpointFactory {

    private final String ENV_VAL_KEY = "FLIGHT_SERVICE_PROD";

    private final String URL_PKG_PREFIXES = "org.jboss.ejb.client.naming";
    private final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private final String PROVIDER_URL = "http-remoting://35.207.154.57:8082";
    //private final String PROVIDER_URL = "http-remoting://localhost:8080";
    private final String LOOKUP_NAME = "ejb:/4/ContractBean!contract.interfaces.BeanInterface";

    public BeanInterface getEndpoint() {
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
                System.out.println(warningMsg);
                // todo logging
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

    private BeanInterface getDevEndpoint() {
        return this.getProductionEndpoint();
    }

    private BeanInterface getTestEndpoint() {
        return null;
    }

    private BeanInterface getProductionEndpoint() {
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
            System.out.println("***********************************************************");
            System.out.println("***********************************************************");
            System.out.println("***********************************************************");
            System.out.println("***********************************************************");
            System.out.println("***********************************************************");
            System.out.println("*********************" + e.getExplanation());
            e.printStackTrace();
            // todo logging
        }
        return null;
    }
}
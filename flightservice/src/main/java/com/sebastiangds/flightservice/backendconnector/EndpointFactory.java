package com.sebastiangds.flightservice.backendconnector;

import contract.interfaces.BeanInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Map;
import java.util.Properties;

public class EndpointFactory {

    private final String ENV_VAL_KEY = "LSD_FE_PROD";

    private final String URL_PKG_PREFIXES = "org.jboss.ejb.client.naming";
    private final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private final String PROVIDER_URL = "http-remoting://localhost:8082";
    private final String LOOKUP_NAME = "ejb:/4/ContractBean!contract.interfaces.BeanInterface";

    public BeanInterface getEndpoint() {

        return this.getProductionEndpoint();
        /*Map<String, String> env = System.getenv();
        boolean isProdEnv = env.containsKey(this.ENV_VAL_KEY) && env.get(this.ENV_VAL_KEY).equals("1");

        if(isProdEnv) {
            BeanInterface endpoint = this.getProductionEndpoint();
            String logMsg = "Prod endpoint initialized";
            System.out.println(logMsg);
            return endpoint;
        }

        String logMsg = "Dev endpoint initialized";
        System.out.println(logMsg);
        return null;*/
    }

    private BeanInterface getProductionEndpoint() {
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
            e.printStackTrace();
        }
        return null;
    }
}

package com.sebastiangds.services;

import com.sebastiangds.flightservice.backendconnector.EndpointFactory;
import logging.SILevel;
import logging.Sender;

import java.util.HashMap;
import java.util.Map;

public class EnvHelper {
    private final String ENV_VAL_KEY = "ENV_TYPE";

    private EnvType envType = EnvType.DEV;
    private final Map<Service, Map<EnvType, ServiceInfo>> serviceMap = new HashMap<>();

    public EnvHelper() {
        populateMap();
    }

    private void setEnvType() {
        Map<String, String> sysEnv = System.getenv();
        if (sysEnv.containsKey(this.ENV_VAL_KEY)) {
            String envVar = sysEnv.get(this.ENV_VAL_KEY);
            try {
                this.envType = EnvType.valueOf(envVar);
            } catch (IllegalArgumentException e) {
                String warningMsg =
                        "Environment " + envVar + " provided by "
                                + this.ENV_VAL_KEY + " not recognised. " +
                                "Continue in " + envType.toString();
                System.out.println(this.ENV_VAL_KEY + " not recognised. " + warningMsg);
                /*Sender sender = new Sender(this.serviceMap.get(Service.RABBIT).get(this.envType).getHost());
                sender.makeLog(
                        EnvHelper.class.getName(),
                        SILevel.WARNING,
                        this.ENV_VAL_KEY + " not recognised",
                        warningMsg);*/
            }
        } else {
            /*Sender sender = new Sender(this.serviceMap.get(Service.RABBIT).get(this.envType).getHost());
            sender.makeLog(
                    EnvHelper.class.getName(),
                    SILevel.WARNING,
                    this.ENV_VAL_KEY + " not set",
                    "Defaulting to " + envType.toString());*/
            System.out.println(this.ENV_VAL_KEY + " not set. " +
                    "Defaulting to " + envType.toString());
        }
    }

    private void populateMap () {
        Map<EnvType, ServiceInfo> feedbackMap = new HashMap<>();
        feedbackMap.put(EnvType.DEV, new ServiceInfo("localhost", 3333));
        feedbackMap.put(EnvType.COMPOSE, new ServiceInfo("feedback-service", 3333));
        this.serviceMap.put(Service.FEEDBACK, feedbackMap);

        Map<EnvType, ServiceInfo> foodMap = new HashMap<>();
        foodMap.put(EnvType.DEV, new ServiceInfo("localhost", 5009));
        foodMap.put(EnvType.COMPOSE, new ServiceInfo("food-service", 5009));
        this.serviceMap.put(Service.FOOD, foodMap);

        Map<EnvType, ServiceInfo> flightMap = new HashMap<>();
        flightMap.put(EnvType.DEV, new ServiceInfo("localhost", 6565));
        flightMap.put(EnvType.COMPOSE, new ServiceInfo("flight-service", 6565));
        this.serviceMap.put(Service.FLIGHT, flightMap);

        Map<EnvType, ServiceInfo> mongoMap = new HashMap<>();
        mongoMap.put(EnvType.DEV, new ServiceInfo("localhost", 27017));
        mongoMap.put(EnvType.COMPOSE, new ServiceInfo("mongodb", 27017));
        this.serviceMap.put(Service.MONGO, mongoMap);

        Map<EnvType, ServiceInfo> rabbitMap = new HashMap<>();
        rabbitMap.put(EnvType.DEV, new ServiceInfo("localhost", 5672, "guest", "guest"));
        rabbitMap.put(EnvType.COMPOSE, new ServiceInfo("rabbitmq", 5672, "guest", "guest"));
        this.serviceMap.put(Service.RABBIT, rabbitMap);
    }

    public ServiceInfo getService(Service serviceName) {
        setEnvType();
        /*Sender sender = new Sender(this.serviceMap.get(Service.RABBIT).get(this.envType).getHost());*/
        ServiceInfo info = this.serviceMap.get(serviceName).get(this.envType);
        /*sender.makeLog(EnvHelper.class.getName(), SILevel.INFO,
                "Retrieved service info for " + serviceName.toString(),
                "Info was host <" + info.getHost() + "> port <" + info.getPort() + ">");*/
        return info;
    }

    public EnvType getEnvType() {
        return this.envType;
    }
}
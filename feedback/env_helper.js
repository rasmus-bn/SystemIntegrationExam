const process = require('process');

const EnvType = Object.freeze({
  "DEV":"DEV",
  "COMPOSE":"COMPOSE",
  "TEST":"TEST",
  "PROD":"PROD",
});

const envValKey = "ENV_TYPE";
let envType = EnvType.DEV;
const serviceMap = {};

const service = Object.freeze({
  "FOOD":"FOOD",
  "FEEDBACK":"FEEDBACK",
  "RABBIT":"RABBIT",
  "FLIGHT":"FLIGHT",
  "MONGO":"MONGO",
});

class ServiceInfo {
  constructor(host, port, userName, password) {
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
  }
}

function getService(serviceName) {
  const info = serviceMap[serviceName][envType];
  console.log(
    "Retrieved service info for " + serviceName.toString(),
    "Info was host " + info.host + ":" + info.port
  );
  return info;
}


function setUp() {
  let envStr = process.env[envValKey];
  if (envStr) {

    envStr = envStr.toString().toUpperCase();

    if (EnvType[envStr]) {
      envType = EnvType[envStr];
    } else {

      const warningMsg = 
        "Environment " + envStr + " provided by "
        + envValKey + " not recognised. " +
        "Continue in " + envType;
      console.log(envValKey + " not recognised. " + warningMsg);
    }
  } else {
    console.log(
      envValKey + " not set. ",
      "Defaulting to " + envType
    );
  }
  populateMap();
}

function populateMap() {
  const feedbackMap = {};
  feedbackMap[EnvType.DEV] = new ServiceInfo("localhost", 3333);
  feedbackMap[EnvType.COMPOSE] = new ServiceInfo("feedback-service", 3333);
  serviceMap[service.FEEDBACK] = feedbackMap;

  const foodMap = {};
  foodMap[EnvType.DEV] = new ServiceInfo("localhost", 5009);
  foodMap[EnvType.COMPOSE] = new ServiceInfo("food-service", 5009);
  serviceMap[service.FOOD] = foodMap;

  const flightMap = {};
  flightMap[EnvType.DEV] = new ServiceInfo("localhost", 6565);
  flightMap[EnvType.COMPOSE] = new ServiceInfo("flight-service", 6565);
  serviceMap[service.FLIGHT] = flightMap;

  const mongoMap = {};
  mongoMap[EnvType.DEV] = new ServiceInfo("localhost", 27017);
  mongoMap[EnvType.COMPOSE] = new ServiceInfo("mongodb", 27017);
  serviceMap[service.MONGO] = mongoMap;

  const rabbitMap = {};
  rabbitMap[EnvType.DEV] = new ServiceInfo("localhost", 5672, "guest", "guest");
  rabbitMap[EnvType.COMPOSE] = new ServiceInfo("rabbitmq", 5672, "guest", "guest");
  serviceMap[service.RABBIT] = rabbitMap;
}

setUp();

exports.envType = envType;
exports.EnvType = EnvType;
exports.service = service;
exports.getService = getService;
exports.ServiceInfo = ServiceInfo;
package com.sebastiangds.flightservice;

import com.sebastiangds.flightservice.lib.FlightServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class FlightServiceImpl extends FlightServiceGrpc.FlightServiceImplBase {
}

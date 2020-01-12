const grpc = require('grpc');
const protoLoader = require('@grpc/proto-loader');
const PROTO_PATH = 'FlightService.proto';

const packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });
console.log(grpc.loadPackageDefinition(packageDefinition));
const flightProto = grpc.loadPackageDefinition(packageDefinition).com.sebastiangds.flightservice;

export const  main = () => {
    const client = new flightProto.FlightService('localhost:6565', grpc.credentials.createInsecure());

    client.getBooking({ bookingId : 2 }, function(err, response) {
        console.log(response);
    });
}
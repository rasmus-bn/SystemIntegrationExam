/**
 * @fileoverview gRPC-Web generated client stub for com.sebastiangds.flightservice
 * @enhanceable
 * @public
 */


// GENERATED CODE -- DO NOT EDIT!
/* eslint-disable */


const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.com = {};
proto.com.sebastiangds = {};
proto.com.sebastiangds.flightservice = require('./FlightService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.com.sebastiangds.flightservice.FlightServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.com.sebastiangds.flightservice.FlightServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.com.sebastiangds.flightservice.GetBookingRequest,
 *   !proto.com.sebastiangds.flightservice.GetBookingReply>}
 */
const methodDescriptor_FlightService_GetBooking = new grpc.web.MethodDescriptor(
  '/com.sebastiangds.flightservice.FlightService/GetBooking',
  grpc.web.MethodType.UNARY,
  proto.com.sebastiangds.flightservice.GetBookingRequest,
  proto.com.sebastiangds.flightservice.GetBookingReply,
  /**
   * @param {!proto.com.sebastiangds.flightservice.GetBookingRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.com.sebastiangds.flightservice.GetBookingReply.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.com.sebastiangds.flightservice.GetBookingRequest,
 *   !proto.com.sebastiangds.flightservice.GetBookingReply>}
 */
const methodInfo_FlightService_GetBooking = new grpc.web.AbstractClientBase.MethodInfo(
  proto.com.sebastiangds.flightservice.GetBookingReply,
  /**
   * @param {!proto.com.sebastiangds.flightservice.GetBookingRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.com.sebastiangds.flightservice.GetBookingReply.deserializeBinary
);


/**
 * @param {!proto.com.sebastiangds.flightservice.GetBookingRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.com.sebastiangds.flightservice.GetBookingReply)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.com.sebastiangds.flightservice.GetBookingReply>|undefined}
 *     The XHR Node Readable Stream
 */
proto.com.sebastiangds.flightservice.FlightServiceClient.prototype.getBooking =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/com.sebastiangds.flightservice.FlightService/GetBooking',
      request,
      metadata || {},
      methodDescriptor_FlightService_GetBooking,
      callback);
};


/**
 * @param {!proto.com.sebastiangds.flightservice.GetBookingRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.com.sebastiangds.flightservice.GetBookingReply>}
 *     A native promise that resolves to the response
 */
proto.com.sebastiangds.flightservice.FlightServicePromiseClient.prototype.getBooking =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/com.sebastiangds.flightservice.FlightService/GetBooking',
      request,
      metadata || {},
      methodDescriptor_FlightService_GetBooking);
};


module.exports = proto.com.sebastiangds.flightservice;


import React from 'react';
import Booking from './Booking'
import Food from './Food'
import Feedback from './Feedback'
//import {main} from './rpcHandler'

import * as grpcWeb from 'grpc-web';
import {EchoServiceClient} from './FlightService_grpc_web_pb';
import {EchoRequest, EchoResponse} from './FlightService_pb';

//var PROTO_PATH = __dirname + '/../../protos/helloworld.proto';

class Root extends React.Component{

  constructor(){
    super();
    //main();
    this.state = {bookingFinished: false, foodFinished: false, feedbackFinished: false, bookingId : null, ticketId : null, foodId : null}
  }


  render(){
    return(<div>
      {this.serviceStepsChecker()}
    </div>)
  }


   
  

  serviceStepsChecker = () => {
    if(this.state.bookingFinished === false){
      return <Booking id={this.state.bookingId} bookingDone={(val, val2) => this.changeState(val, val2)}/>
    }
    if(this.state.bookingFinished === true && this.state.foodFinished === false){
      return <Food bookingId={this.state.bookingId} ticketId={this.state.ticketId} foodDone={(val, val2) => this.changeState(val, val2)} ticketId={this.state.ticketId}done={(val) => this.changeState(val)}/>
    }
    if(this.state.bookingFinished === true && this.state.foodFinished === true){
      return <Feedback choices={this.state} feedbackDone={(val, val2) => this.changeState(val,val2)}/>
    }
  }
  changeState = (choice, id) => {
    if(choice === "booking done"){
      this.setState({bookingFinished: true})
    }
    if(choice === "food done"){
      this.setState({foodFinished: true, foodId : id})
    }
    if(choice === "feedback done"){
      this.setState({feedbackFinished: true})
    }
    if(choice === "booking id"){
      this.setState({bookingId : id});
    }
    if(choice === "ticket id"){
      this.setState({ticketId : id});
    }
  }
}



export default Root;
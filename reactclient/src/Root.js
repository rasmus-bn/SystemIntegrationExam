import React from 'react';
import Booking from './Booking'
import Food from './Food'
import Feedback from './Feedback'
import {getAllCategories} from './data'
class Root extends React.Component{

  constructor(){
    super();
    this.state = {bookingFinished: false, foodFinished: false, feedbackFinished: false, bookingId : null, ticketId : null}
  }


  render(){
    return(<div>
      {this.serviceStepsChecker()}
    </div>)
  }


   
  

  serviceStepsChecker = () => {
    if(this.state.bookingFinished === false){
      return <Booking id={this.state.bookingId} done={(val, val2) => this.changeState(val, val2)}/>
    }
    if(this.state.bookingFinished === true && this.state.foodFinished === false){
      return <Food bookingId={this.state.bookingId} ticketId={this.state.ticketId}done={(val) => this.changeState(val)}/>
    }
    if(this.state.feedbackFinished === true && this.state.foodFinished === true){
      return <Feedback done={(val) => this.changeState(val)}/>
    }
  }
  changeState = (choice, id) => {
    if(choice === "booking done"){
      this.setState({bookingFinished: true})
    }
    if(choice === "food done"){
      this.setState({foodFinished: true})
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

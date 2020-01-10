import React from 'react';
import Booking from './Booking'
import Food from './Food'
import Feedback from './Feedback'

class Root extends React.Component{

  constructor(){
    super();
    this.state = {bookingFinished: false, foodFinished: false, feedbackFinished: false}
  }
  render(){
    return(<div>
      {this.serviceStepsChecker()}
    </div>)
  }

  serviceStepsChecker = () => {
    if(this.state.bookingFinished === false){
      return <Booking done={(val) => this.changeState(val)}/>
    }
    if(this.state.bookingFinished === true && this.state.foodFinished === false){
      return <Food done={(val) => this.changeState(val)}/>
    }
    if(this.state.feedbackFinished === true && this.state.foodFinished === true){
      return <Feedback done={(val) => this.changeState(val)}/>
    }
  }
  changeState = (choice) => {
    if(choice === "booking done"){
      this.setState({bookingFinished: true})
    }
    if(choice === "food done"){
      this.setState({foodFinished: true})
    }
    if(choice === "feedback done"){
      this.setState({feedbackFinished: true})
    }
  }
}



export default Root;

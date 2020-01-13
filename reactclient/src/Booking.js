import React from 'react'

export default class Booking extends React.Component {
    constructor() {
        super();
        this.state = { showTickets: false, ticketId : null, ticketIds : [], airportNames : []}
    }


    generateButtons = () => {
      var arr = [];
      for (let index = 0; index < this.state.ticketIds.length; index++) {
        arr.push(<li>
          <label>
            <input
              type="radio"
              value={this.state.ticketIds[index]}
              checked={this.state.size === "small"}
              onChange={this.handleChange}
            />
            {this.state.airportNames[index]} (id {this.state.ticketIds[index]})
          </label>
        </li>)
      }
      return arr;
    }
    getCurrentPicked = () => {
      var getPicked;
      for (let index = 0; index < this.state.ticketIds.length; index++) {
        if(this.state.ticketIds[index] === this.state.ticketId){
          getPicked =  <p>currently picked: {this.state.airportNames[index].split(" from")[0] + " id: " + this.state.ticketId}</p>
          break;
        }
       
      } return getPicked;
    }

    render() {
        return (<div>
            <form onSubmit={this.showTickets}>
                <input placeholder="booking id" />
                <input type="submit" />
            </form>
        {this.state.showTickets ? <form onSubmit={this.doneWithBooking}>
      <p>Select a ticket:</p>
      
      <ul>
        {this.generateButtons()}
      </ul>

      <button type="submit">Make your choice</button>
        {this.getCurrentPicked()}
    </form>:'please pick an id'}

            

        </div>)
    }
    handleChange = (event) => {
        this.setState({
          ticketId: event.target.value
        });
      }

    showTickets = (e) => {
        this.setState({showTickets: true});
        fetch("http://localhost:4444/food/api/v1/food/bookings?id=" + e.target[0].value, {
          method: "get",
          headers: {'Content-Type': 'application/json' }
        }).then((res)=>{
          return res.json();
        }).then((json)=>{
          var arrIds = [];
          var arrAnames = [];
          for (let index = 0; index < json.booking.tickets.length; index++) {
            arrIds.push(json.booking.tickets[index].ticketId);
            arrAnames.push(json.booking.tickets[index].firstName + " " + json.booking.tickets[index].lastName + " from " +  json.booking.tickets[index].depAiportName + " to " + json.booking.tickets[index].arrAiportName);
          }
        
          this.setState({ticketIds : arrIds, airportNames : arrAnames});
        })
        this.props.bookingDone("booking id", e.target[0].value);
        e.preventDefault();
    }

    doneWithBooking = (e) => {
        this.props.bookingDone("ticket id", this.state.ticketId);
        this.props.bookingDone("booking done");
        e.preventDefault();
    }
}
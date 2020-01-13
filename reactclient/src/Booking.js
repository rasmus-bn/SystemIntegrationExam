import React from 'react'

export default class Booking extends React.Component {
    constructor() {
        super();
        this.state = { showTickets: false, ticketId : null}
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
        <li>
          <label>
            <input
              type="radio"
              value="1"
              checked={this.state.size === "small"}
              onChange={this.handleChange}
            />
            ticket1 (id 1)
          </label>
        </li>
        
        <li>
          <label>
            <input
              type="radio"
              value="1"
              checked={this.state.size === "medium"}
              onChange={this.handleChange}
            />
            ticket2 (id 1)
          </label>
        </li>

        <li>
          <label>
            <input
              type="radio"
              value="1"
              checked={this.state.size === "large"}
              onChange={this.handleChange}
            />
            ticket3 (id 1)
          </label>
        </li>
      </ul>

      <button type="submit">Make your choice</button>
        <p>currently picked: {this.state.ticketId}</p>
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
        this.props.bookingDone("booking id", e.target[0].value);
        e.preventDefault();
    }

    doneWithBooking = (e) => {
        this.props.bookingDone("ticket id", this.state.ticketId);
        this.props.bookingDone("booking done");
        e.preventDefault();
    }
}
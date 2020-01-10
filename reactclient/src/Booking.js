import React from 'react'

export default class Booking extends React.Component {
    constructor() {
        super();
        this.state = { showTickets: false, idPicked : null }
    }

    render() {
        return (<div>
            <form onSubmit={this.showTickets}>
                <input placeholder="booking id" />
                <input type="submit" />
            </form>
            {this.state.showTickets ? <form onSubmit={this.doneWithBooking}>
                <input type="radio" name="ticketChoice" value="ticket1" />ticket1<br />
                <input type="radio" name="ticketChoice" value="ticket2" />ticket2<br />
                <input type="radio" name="ticketChoice" value="ticket3" />ticket3<br />
                <input type="radio" name="ticketChoice" value="ticket4" />ticket4<br />
                <input type="radio" name="ticketChoice" value="ticket5" />ticket5<br />
                <input type="radio" name="ticketChoice" value="ticket6" />ticket6<br />
                <input type="submit" />
            </form>:'please pick an id'}

        </div>)
    }
    showTickets = (e) => {
        this.setState({showTickets: true});
        e.preventDefault();
    }

    doneWithBooking = (e) => {
        this.props.done("booking done");
        e.preventDefault();
    }
}
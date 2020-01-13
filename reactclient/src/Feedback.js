import React from 'react'

export default class Feedback extends React.Component{
    constructor(){
        super();
    }

saveFeedback = (e) => {
    var bookingId = this.props.choices.bookingId
    var ticketId = this.props.choices.ticketId
    var foodId = this.props.choices.foodId

    var name = e.target[0].value;
    var age = e.target[1].value;
    var gender = e.target[2].value;
    var location = e.target[3].value;
    var rating = e.target[4].value;
    var feedback = e.target[5].value;
    console.log(bookingId);
    console.log(ticketId);
    console.log(foodId);
    console.log(name);
    console.log(age);
    console.log(gender);
    console.log(location);
    console.log(rating);
    console.log(feedback);
    
    var data = {name: name, age: age, location: location, gender: gender, feedback: feedback, rating: rating}

    fetch("http://localhost:4444/feedback/savefeedback",{method: "post", headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)}).then((res)=>{
        return res.text();
    }).then((text)=>{
        alert(text);
    })

    e.preventDefault();
}

    render(){
        return(<div>
            <h1>thank you! we would love some feedback if possible :)</h1>
            <form onSubmit={this.saveFeedback}>
                <input placeholder="name"/>
                <input placeholder="age"/>
                <input placeholder="gender"/>
                <input placeholder="location"/>
                <input placeholder="rating"/>
                <input placeholder="feedback"/>
                <input type="submit"/>
            </form>
        </div>)
    }
}
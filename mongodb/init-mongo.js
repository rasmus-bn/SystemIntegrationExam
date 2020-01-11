db.createUser({
    user : "hallur",
    pwd : "123",
    roles : [
        {
            role: "readWrite",
            db: "your-database-name"
        }
    ]
})

db.feedback.insert(
    {name: "karl", age: "20", location: "copenhagen", gender: "male/female/other", feedback: "i am not happy", rating: 1})

db.booking.insert({bookingId: 1, 
    tickets: [{ticketId: 1, 
        foods: [{name: "peanuts", description: "peanuts description"}, {name: "sandwich", description: "sandwich description"}],
               ticketId: 2,
        foods: [{name: "chicken nuggets", description: "nuggets description"}, {name: "salt", description: "nuggets description"}]
    }]});
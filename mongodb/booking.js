use booking

db.booking.insert({bookingId: 1, 
    tickets: [{ticketId: 1, 
        foods: [{name: "peanuts", description: "peanuts description"}, {name: "sandwich", description: "sandwich description"}],
               ticketId: 2,
        foods: [{name: "chicken nuggets", description: "nuggets description"}, {name: "salt", description: "nuggets description"}]
    }]});

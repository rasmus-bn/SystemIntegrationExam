db.dropUser("food-service");
db.createUser({
    user: "food-service",
    pwd: "food123",
    roles: [
        { role: "readWrite", db: "food" }
    ],
});
db.dropUser("feedback-service");
db.createUser({
    user: "feedback-service",
    pwd: "feedback123",
    roles: [
        { role: "readWrite", db: "feedback" }
    ],
});

use food
db.booking.insert({bookingId: 1, 
    tickets: [{ticketId: 1, 
        foods: [{name: "peanuts", description: "peanuts description"}, {name: "sandwich", description: "sandwich description"}],
               ticketId: 2,
        foods: [{name: "chicken nuggets", description: "nuggets description"}, {name: "salt", description: "nuggets description"}]
    }]});
    
use feedback
db.feedback.insert({
    name: "karl", 
    age: "20", 
    location: "copenhagen", 
    gender: "male/female/other", 
    feedback: "i am not happy", 
    rating: 1
    });
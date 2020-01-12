const env = require('../env_helper');
var mongoose = require('mongoose')
  , MONGO_DB = 'mongodb://' + env.getService(env.service.MONGO).host +'/feedback';

mongoose.connect(MONGO_DB);

mongoose.connection.on('open', function (ref) {
    console.log('Connected to mongo server.');
    //trying to get collection names
    mongoose.connection.db.listCollections().toArray(function (err, names) {
        console.log(names); // [{ name: 'dbname.myCollection' }]
        module.exports.Collection = names;
    });
})


//var mongoose = require('mongoose');
//mongoose.connect('mongodb://localhost/feedback', {useNewUrlParser: true});

//var db = mongoose.connection;

var feedbackSchema = new mongoose.Schema({
    name: String,
    age: String,
    location: String,
    gender: String,
    feedback: String,
    rating: Number
  }, { collection: 'feedback' });

var Feedback = mongoose.model("feedback", feedbackSchema);



saveFeedback = (name, age, location, gender, feedback, rating) => {
    var saveThis = new Feedback({
        name: name,
        age: age,
        location: location,
        gender: gender,
        feedback: feedback,
        rating: rating
    })
    saveThis.save((err)=>{if(err){
        console.log(err);
    } else {
        console.log("saved!");
    }})
}

//saveFeedback("wqe", "weqopk", "ekwpoq", "qwejkiop", "qweiojkp", 2);
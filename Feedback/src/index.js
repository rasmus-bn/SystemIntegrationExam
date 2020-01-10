const backend = require('./backend');
const express = require('express');
const cors = require('cors')
const bodyParser = require('body-parser');

backend.initialSetup();

const app = express();
app.use(cors())
const jsonParser = bodyParser.json();
const port = 3333;

app.get('/help', (req, res) => {
    const msg = `
    Following ressources are available from the feedback service:
    \n<br/>* /feedback
    `;
    res.send(msg);
});

app.post('/feedback', jsonParser, (req, res) => {
    console.log(req.body);
    req.body.age = req.body.upperAge;
    backend.createFeedback(req.body.rating, req.body.foodId, req.body).then(() => {
        return res.send(wrapResult(null, true, `Feedback inserted with foodId ${req.body.foodId}`));
    }).catch((err) => {
        return handleError(err, res);
    });
});

app.post('/feedback/help' , (req, res) => {
    const expectedformat = {
        foodId : "int", 
        rating : "int in range 1-5", 
        description : "(optional) string", 
        gender : "(optional) string", 
        countryName : "(optional) string", 
        age : "(optional) string"
    }
    res.send(wrapResult(null, true, `Expected format in json body: ${JSON.stringify(expectedformat)}`));
});

app.get('/feedback/:id', (req, res) => {
    const id = req.params.id;

    backend.readFeedbackById(id).then((feedback) => {
        feedback.age = feedback.upperAge;
        if (feedback) return res.send(wrapResult(feedback, true));
        return res.send(wrapResult(feedback, false, `No feedback found with id ${id}`));
    }).catch((err) => {
        return handleError(err, res);
    });
});

app.get('/feedback', (req, res) => {
    const foodId = req.query.foodId;

    backend.readFeedbackByFoodId(foodId).then((feedback) => {
        feedback.age = feedback.upperAge;
        if (feedback.length > 0) return res.send(wrapResult(feedback, true));
        return res.send(wrapResult(feedback, false, `No feedback found with foodId ${foodId}`));
    }).catch((err) => {
        return handleError(err, res);
    });
});

app.put('/feedback/:id', jsonParser, (req, res) => {
    const id = req.params.id;
    req.body.age = req.body.upperAge;
    backend.updateFeedback(id, req.body.rating, req.body.foodId, req.body).then(() => {
        return res.send(wrapResult(null, true, `Feedback updated at id ${id}`));
    }).catch((err) => {
        return handleError(err, res);
    });
});

app.delete('/feedback/:id', (req, res) => {
    const id = req.params.id;
    backend.deleteFeedback(id).then(() => {
        return res.send(wrapResult(null, true, `Feedback deleted at id ${id}`));
    }).catch((err) => {
        return handleError(err, res);
    });
});

function wrapResult(result, succes, message = null) {
    if (!message) {
        message = succes ? 'succes' : 'no message';
    }
    return {
        result : result,
        succes : succes,
        message : message,
    };
}
function handleError(err, res) {
    console.log(err);
    if (err.name === backend.UserExposedError) {
        return res.status(400).send(wrapResult(null, false, err.userMessage));
    }
    if (err.name === backend.DBError) {
        console.log("DBError");
    }
    return res.status(500).send(wrapResult(null, false, 'The server encountered an error'));
}

app.listen(port, () => { console.log(`Feedback service listening on port ${port}`) });
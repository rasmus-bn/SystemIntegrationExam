const proxy = require('express-http-proxy');
const app = require('express')();
const cors = require('cors')
const port = 4444;

app.use('/feedback', proxy('http://feedback-service:3333'));
app.use('/food', proxy('http://food-service:5009'));
app.use(cors());

app.listen(port, () => { console.log(`Gateway listening on port ${port}`) });
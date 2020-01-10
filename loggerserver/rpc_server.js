var amqp = require('amqplib/callback_api');
const fs = require('fs');
var csvWriter = require('csv-write-stream')
var writer = csvWriter()

amqp.connect('amqp://localhost', function (error0, connection) {
    if (error0) {
        throw error0;
    }
    connection.createChannel(function (error1, channel) {
        if (error1) {
            throw error1;
        }
        var queue = 'rpc_queue';

        channel.assertQueue(queue, {
            durable: false
        });
        channel.prefetch(1);
        console.log(' [x] Awaiting RPC requests');
        channel.consume(queue, function reply(msg) {
            var n = "" + msg.content;
            var arr = n.split(", ");
            var severity = arr[0];
            var description = arr[1];
            var message = arr[2];

            console.log("received log: ", n);
            saveLog(severity, description, message);

            //var r = fibonacci(n);

             channel.sendToQueue(msg.properties.replyTo,
                 Buffer.from("log was saved"), {
                     correlationId: msg.properties.correlationId
                 });
   
            channel.ack(msg);
        });
    });
});

saveLog = (severity, description, message) => {
    var today = new Date();

    var timeStamp = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate() + " " + today.getHours() + ":" + today.getMinutes();
    //sessionId, value, severity, keyword, description, condition
    if (!fs.existsSync("./log.csv"))
        writer = csvWriter({ headers: ["header1", "header2"] });
    else
        writer = csvWriter({ sendHeaders: false });

    writer.pipe(fs.createWriteStream("./log.csv", { flags: 'a' }));
    writer.write({
        timeStamp: timeStamp,
        value: severityValue(severity),
        severity: severity,
        description: description,
        message: message
    });
    writer.end();
}

severityValue = (severity) => {
    switch (severity) {
        case "SEVERE":
            return 0;
        case "ALERT":
            return 1;
        case "CRITICAL":
            return 2;
        case "ERROR":
            return 3;
        case "WARNING":
            return 4;
        case "NOTICE":
            return 5;
        case "INFO":
            return 6;
        case "DEBUG":
            return 7;
        default:
            return -1;
    }
}
var amqp = require('amqplib/callback_api');

module.exports = {
    makeLog: function(level, description, message) {
        amqp.connect('amqp://localhost', function(error0, connection) {
        if (error0) {
          throw error0;
        }
        connection.createChannel(function(error1, channel) {
          if (error1) {
            throw error1;
          }
          channel.assertQueue('', {
            exclusive: true
          }, function(error2, q) {
            if (error2) {
              throw error2;
            }
            var correlationId = generateUuid();
            var msg = level + ", " + description + ", " + message;
      
            console.log(' [x] Requesting fib(%d)', msg);
      
            channel.consume(q.queue, function(msg) {
              if (msg.properties.correlationId == correlationId) {
                console.log(' [.] Got %s', msg.content.toString());
                setTimeout(function() { 
                  connection.close(); 
                  process.exit(0) 
                }, 500);
              }
            }, {
              noAck: true
            });
      
            channel.sendToQueue('rpc_queue',
              Buffer.from(msg.toString()),{ 
                correlationId: correlationId, 
                replyTo: q.queue });
          });
        });
      });
    },
    multiply: function(a,b) {
        return a*b
    }
};

function generateUuid() {
    return Math.random().toString() +
           Math.random().toString() +
           Math.random().toString();
  }
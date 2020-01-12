package logging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sebastiangds.services.EnvHelper;
import com.sebastiangds.services.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class Sender implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "rpc_queue";
    private final String HOST_NAME;

    public Sender() {
        this.HOST_NAME = new EnvHelper().getService(Service.RABBIT).getHost();
        connectToRabbit();
    }

    private boolean connectToRabbit() {
        if (connection == null || !connection.isOpen()) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(this.HOST_NAME);
            try {
                connection = factory.newConnection();
            } catch (IOException e) {
                connection = null;
                return false;
            } catch (TimeoutException e) {
                connection = null;
                return false;
            }
        }

        if (connection != null) {
            if  (channel == null || !channel.isOpen()) {
                try {
                    channel = connection.createChannel();
                } catch (IOException e) {
                    channel = null;
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void makeLog(String className, SILevel level, String description, String msg){
        if (!connectToRabbit()) return;
        Message message = new Message(""+level.toString(), description, msg);
        System.out.println(message.toString());
        sendToServer(message);
    }

    public String sendToServer(Message message) {
        String response = "";
        try (Sender logRpc = new Sender()) {
            String toLog = message.toString();
            System.out.println("sending log...");
            response = logRpc.call(toLog);
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;

    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", this.QUEUE_NAME, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}


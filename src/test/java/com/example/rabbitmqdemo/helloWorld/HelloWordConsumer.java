package com.example.rabbitmqdemo.helloWorld;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.example.rabbitmqdemo.constant.QueueNameConstant.HELLO_QUEUE_NAME;

public class HelloWordConsumer {

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();
        channel.queueDeclare(HELLO_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" 接收的消息 '" + message + "'");
        };

        channel.basicConsume(HELLO_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
//        RabbitUtils.close(channel,connection);
    }

}

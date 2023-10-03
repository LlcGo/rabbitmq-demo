package com.example.rabbitmqdemo.workqueue;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.rabbitmqdemo.constant.QueueNameConstant.WORK_QUEUE_NAME;

public class WorkQC2 {


    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" 接收的消息wORK2 '" + message + "'");
        };

        channel.basicConsume(WORK_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

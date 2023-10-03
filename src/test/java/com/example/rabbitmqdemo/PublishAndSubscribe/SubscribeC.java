package com.example.rabbitmqdemo.PublishAndSubscribe;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.PUBLISH_EXCHANGE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.PUBLISH_SUBSCRIBE_QUEUE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.WORK_QUEUE_NAME;

public class SubscribeC {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(PUBLISH_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(PUBLISH_SUBSCRIBE_QUEUE_NAME, false, false, false, null);

        //消费者队列与交换机做绑定根据路由key
        channel.queueBind(PUBLISH_SUBSCRIBE_QUEUE_NAME,PUBLISH_EXCHANGE_NAME,"");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" 接收的消息WORK0 '" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(PUBLISH_SUBSCRIBE_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

}

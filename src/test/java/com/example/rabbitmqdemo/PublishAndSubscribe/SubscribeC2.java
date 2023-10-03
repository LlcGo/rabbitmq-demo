package com.example.rabbitmqdemo.PublishAndSubscribe;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.PUBLISH_EXCHANGE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.PUBLISH_SUBSCRIBE_QUEUE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.PUBLISH_SUBSCRIBE_QUEUE_NAME2;

public class SubscribeC2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(PUBLISH_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(PUBLISH_SUBSCRIBE_QUEUE_NAME2, false, false, false, null);

        //绑定交换机
        channel.queueBind(PUBLISH_SUBSCRIBE_QUEUE_NAME2,PUBLISH_EXCHANGE_NAME,"");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" 接收的消息WORK0 '" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(PUBLISH_SUBSCRIBE_QUEUE_NAME2, false, deliverCallback, consumerTag -> { });
    }

}

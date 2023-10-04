package com.example.rabbitmqdemo.TTL;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.TOPIC_EXCHANGE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.PUBLISH_SUBSCRIBE_QUEUE_NAME;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.TTL_MESSAGE_QUEUE;

public class SubscribeC {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //TTL设置
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x-message-ttl", 10000);
        channel.queueDeclare(TTL_MESSAGE_QUEUE, false, false, false, params);

        //消费者队列与交换机做绑定根据路由key
        channel.queueBind(TTL_MESSAGE_QUEUE,TOPIC_EXCHANGE_NAME,"*.orange");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" orange ----> '" + message + "'");
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(TTL_MESSAGE_QUEUE, false, deliverCallback, consumerTag -> { });
    }

}

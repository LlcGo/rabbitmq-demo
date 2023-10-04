package com.example.rabbitmqdemo.DXL;

import com.example.rabbitmqdemo.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.*;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.DEAD_MESSAGE_QUEUE;
import static com.example.rabbitmqdemo.constant.QueueNameConstant.TTL_MESSAGE_QUEUE;

public class SubscribeC {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getRabbitMqChannel();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);



        //队列参数设置
        Map<String, Object> params = new HashMap<String, Object>();

        //创建死信交换机
        channel.exchangeDeclare(DEAD_LETTER_NAME, BuiltinExchangeType.FANOUT);

        //设置死信队列
        params.put("x-dead-letter-exchange", DEAD_LETTER_NAME);
        channel.queueDeclare(DEAD_MESSAGE_QUEUE, false, false, false, params);


        //消费者队列与交换机做绑定根据路由key
        channel.queueBind(DEAD_MESSAGE_QUEUE,DIRECT_EXCHANGE_NAME,"dead_line_test");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" 正常接收到的数据（但是我没有签收） ----> '" + message + "'");
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false,false);
        };

        channel.basicConsume(DEAD_MESSAGE_QUEUE, false, deliverCallback, consumerTag -> { });
    }

}

package com.example.rabbitmqdemo.TTL;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.TOPIC_EXCHANGE_NAME;

public class PublishQP {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.113.223.242");
        factory.setUsername("root");
        factory.setPassword("rabbitmqPassWord@666.");
//        factory.setHandshakeTimeout(300000000);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //声明交换机
            channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            Scanner scanner = new Scanner(System.in);
            String message = null;
            while (scanner.hasNext()) {
                message = scanner.nextLine();
                Optional.ofNullable(message).orElse("kong shuju");
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .expiration("60000")
                        .build();
                channel.basicPublish(TOPIC_EXCHANGE_NAME, "A.orange", properties, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

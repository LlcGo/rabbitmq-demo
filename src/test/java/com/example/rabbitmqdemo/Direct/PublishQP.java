package com.example.rabbitmqdemo.Direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.DIRECT_EXCHANGE_NAME;
import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.PUBLISH_EXCHANGE_NAME;

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
            channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            Scanner scanner = new Scanner(System.in);
            String message = null;
            while (scanner.hasNext()) {
                message = scanner.nextLine();
                Optional.ofNullable(message).orElse("kong shuju");
                channel.basicPublish(DIRECT_EXCHANGE_NAME, "orange", null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

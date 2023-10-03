package com.example.rabbitmqdemo.helloWorld;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.example.rabbitmqdemo.constant.QueueNameConstant.HELLO_QUEUE_NAME;

public class HelloWordProduce {



    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.113.223.242");
        factory.setUsername("root");
        factory.setPassword("rabbitmqPassWord@666.");
//        factory.setHandshakeTimeout(300000000);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(HELLO_QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            String message = null;
            while (scanner.hasNext()) {
                message = scanner.nextLine();
                Optional.ofNullable(message).orElse("kong shuju");
                channel.basicPublish("", HELLO_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
package com.example.rabbitmqdemo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtils {

    public static Connection getRabbitMqChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.113.223.242");
        factory.setUsername("root");
        factory.setPassword("rabbitmqPassWord@666.");
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void close(Channel channel,Connection connection){
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}

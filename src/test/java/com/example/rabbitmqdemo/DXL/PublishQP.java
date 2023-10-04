package com.example.rabbitmqdemo.DXL;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.example.rabbitmqdemo.constant.QueueExchangeConstant.*;

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
//                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                        .expiration("60000")
//                        .build();
                channel.basicPublish(DIRECT_EXCHANGE_NAME, "dead_line_test",  null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

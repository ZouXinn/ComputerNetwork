package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.server.ignite.IgniteHandler;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServerApplication {

    public static void main(String[] args) {
        //IgniteHandler.CreateTable();
        SpringApplication.run(ServerApplication.class, args);
    }

}

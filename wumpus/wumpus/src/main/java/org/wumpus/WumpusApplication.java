package org.wumpus;

import org.wumpus.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class WumpusApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WumpusApplication.class);

    private final CommandService commandService;

    public WumpusApplication(CommandService commandService) {
        this.commandService = commandService;
    }


    public static void main(String[] args) {
        SpringApplication.run(WumpusApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Welcome gamer! This is the wumpus game!");
        Scanner in = new Scanner(System.in);
        commandService.collectUserName(in);
    }
}

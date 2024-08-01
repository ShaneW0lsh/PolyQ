package ru.kolomych.polyq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PolyqApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(PolyqApplication.class, args);
	}
}

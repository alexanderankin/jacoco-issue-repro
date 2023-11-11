package org.example.jacoco.repro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JacocoDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(JacocoDemoApp.class, args);
    }
}

class ClassToTest {
    String result(String input, Action action) {
        if (action == null)
            throw new IllegalArgumentException("no action");

        var result = switch (action) {
            case CONCAT -> input + action;
            case LENGTH -> String.valueOf(input.length());
        };

        System.out.println("result is: " + result);
        return result;
    }

    enum Action {CONCAT, LENGTH}
}

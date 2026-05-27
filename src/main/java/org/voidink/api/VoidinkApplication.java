package org.voidink.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoidinkApplication {

    public static void main(String[] squadron) {
        // Este método levanta el servidor embebido Tomcat en el puerto asignado
        SpringApplication.run(VoidinkApplication.class, squadron);
    }
}
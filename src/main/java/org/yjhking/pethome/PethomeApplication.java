package org.yjhking.pethome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.yjhking.pethome.*.mapper")
public class PethomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PethomeApplication.class, args);
    }
}

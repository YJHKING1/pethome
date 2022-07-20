package org.yjhking.pethome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("org.yjhking.pethome.*.mapper")
@ServletComponentScan(value = {"org.yjhking.pethome.basic.listener"})
public class PethomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PethomeApplication.class, args);
    }
}

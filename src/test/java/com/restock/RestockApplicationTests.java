package com.restock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.uri=mongodb://localhost:27017/restock-test",
    "mqtt.enabled=false"
})
class RestockApplicationTests {

    @Test
    void contextLoads() {
    }
}


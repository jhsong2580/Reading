package study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

class HelloControllerTest {
    @ParameterizedTest
    @ValueSource(strings = {"a","b","c"})
    public void helloAPI (String name){
        //given
        TestRestTemplate rest = new TestRestTemplate();
        Map<String, String> urlVals = new HashMap<>();
        urlVals.put("name", name);

        //when
        ResponseEntity<String> response = rest.getForEntity(
            "http://localhost:8080/hello?name={name}", String.class, urlVals);

        //then
        assertAll(
            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(response.getBody()).isEqualTo("*Hello ! " + name + "*"),
            () -> assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                .startsWith(MediaType.TEXT_PLAIN_VALUE)
        );
    }

    @Test
    public void HelloControllerTest (){
        //given
        HelloController helloController = new HelloController(name -> name);

        //when
        ResponseEntity<?> test = helloController.hello("test");
        //then
    }
}
package study.controller;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.prometheus.client.Counter;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/login")
    public String yourHandler() {
        // Handle your request here
        // Increment the counter for each incoming request
        List<Tag> tags = List.of(
            Tag.of("path", "login")
        );
        Metrics.counter("http_requests", List.of(Tag.of("path", "login0"), Tag.of("method", "GET"))).increment();
        Metrics.counter("http_requests", List.of(Tag.of("path", "login1"), Tag.of("method", "GET"))).increment();
        Metrics.counter("http_requests", List.of(Tag.of("path", "login2"), Tag.of("method", "POST"))).increment();
        Metrics.counter("http_requests", List.of(Tag.of("path", "login3"), Tag.of("method", "POST"))).increment();
        Metrics.counter("http_requests", List.of(Tag.of("path", "login4"), Tag.of("method", "DELETE"))).increment();
        Iterable<Measurement> measure = Metrics.summary("http_requests", List.of(Tag.of("path", "login0"))).measure();
        return "Your response";
    }

    @GetMapping("")
    public String a(){
        return "hi!";
    }
}

package study.event;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import study.format.MyEvent;
import study.format.MySubEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static int runningId = 0;
    private static AtomicInteger id = new AtomicInteger(1);
    private final KafkaTemplate kafkaProducer;

    @Scheduled(fixedRate = 1000 * 10, initialDelay = 5 * 1000)
    public void produce() {
        MySubEvent sub = new MySubEvent(id.getAndIncrement(), 4L);
        MyEvent myEvent = new MyEvent("1", 2, sub);
        kafkaProducer.send(new ProducerRecord("hello.kafka.11", myEvent));
    }

//    @Scheduled(fixedRate = 1000*10, initialDelay = 5*1000)
//    public void produceMessage() {
//
//        String message = String.format("%d 번째 메세지를 %s 에 전송 하였습니다.", runningId++, LocalDateTime.now().toString());
//        log.info(message);
//        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("hello.kafka.11", message);
//        listenableFuture.addCallback(new ListenableFutureCallback<Object>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error("ERROR Kafka error happend", ex);
//            }
//
//            @Override
//            public void onSuccess(Object result) {
//                log.info("SUCCESS!! This is the reulst: {}", result);
//            }
//        });
//
//        log.info("Produce Message - END {}", message);
//    }

}
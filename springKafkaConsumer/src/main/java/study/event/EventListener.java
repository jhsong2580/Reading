package study.event;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import study.format.MyEvent;

@Component
@RequiredArgsConstructor
public class EventListener {




    @KafkaListener(id = "listener1", topics = "hello.kafka.11", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void consumer1(MyEvent myEvent, Acknowledgment ack) {
        System.out.println("CONSUMER_GROUP_1 : LISTENER_1 : " + myEvent);
        ack.acknowledge();
    }

    @KafkaListener(id = "listener2", topics = "hello.kafka.11", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void consumer2(MyEvent myEvent, Acknowledgment ack) {
        System.out.println("CONSUMER_GROUP_1 : LISTENER_2 : " + myEvent);
        ack.acknowledge();
    }


    @KafkaListener(id = "listener3", topics = "hello.kafka.11", groupId = "group2", containerFactory = "kafkaListenerContainerFactory")
    public void consumer3(MyEvent myEvent, Acknowledgment ack) {
        System.out.println("CONSUMER_GROUP_2 : LISTENER_3 : " + myEvent);
        ack.acknowledge();
    }


    @KafkaListener(id = "listener4", topics = "hello.kafka.11", groupId = "group2", containerFactory = "kafkaListenerContainerFactory")
    public void consumer4(MyEvent myEvent, Acknowledgment ack) {
        System.out.println("CONSUMER_GROUP_2 : LISTENER_4 : " + myEvent);
        // 볼륨 생성
        try{
            ack.acknowledge();
        }catch(Exception e){
            ack.nack(Duration.ZERO);
        }


    }

//        @KafkaListener(id = "listener1", topics = "hello.kafka.11", groupId = "group1")
//    public void listener1(MyEvent myEvent, ConsumerRecord<Object, MyEvent> record, Acknowledgment acknowledgment)
//        throws InterruptedException {
//        MyEvent value = record.value();
//
//        System.out.println("LISTENER 1 : " + myEvent);
//        System.out.println(acknowledgment);
//        Thread.sleep(2000);
//    }
    //
//    @KafkaListener(id = "listener1", topics = "hello.kafka.11", groupId = "group1")
//    public void listener1(MyEvent myEvent, ConsumerRecord<Object, MyEvent> record, Acknowledgment acknowledgment)
//        throws InterruptedException {
//        MyEvent value = record.value();
//
//        System.out.println("LISTENER 1 : " + myEvent);
//        System.out.println(acknowledgment);
//        Thread.sleep(2000);
//    }
//
//    @KafkaListener(id = "listener2", topics = "hello.kafka.11", groupId = "group1")
//    public void listener2(MyEvent myEvent) throws InterruptedException {
//
//        System.out.println("LISTENER 2 : " + myEvent);
//        Thread.sleep(2000);
//    }
//
//    @KafkaListener(id = "listener3", topics = "hello.kafka.11", groupId = "group2")
//    public void listener3(MyEvent myEvent, ConsumerRecord<Object, MyEvent> record)
//        throws InterruptedException {
//        ConsumerRecord<Object, MyEvent> record1 = record;
//
//        System.out.println("LISTENER 3 : " + myEvent);
//        Thread.sleep(2000);
//    }
//
//    @KafkaListener(id = "listener4", topics = "hello.kafka.11", groupId = "group2")
//    public void listener4(MyEvent myEvent, ConsumerRecord<Object, MyEvent> record)
//        throws InterruptedException {
//        System.out.println("LISTENER 4 : " + record);
//        System.out.println("LISTENER 4 : " + myEvent);
//        Thread.sleep(2000);
//
//    }
}
package ch11;

import domain.ch11.StopThread;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Example {
    private ExecutorService exec;

    @BeforeEach
    public void init() {
        exec =  Executors.newSingleThreadExecutor(); // 작업 큐 생성
    }

    @Test
    public void Executor학습_예시 (){

        exec.execute(() -> {
            for(int i=0; i<100_000_000; i++) {
                System.out.println(i);
                sleep();
            }
        }); // 실행할 태스크 넘기기

        exec.shutdown(); // Thread 종료
    }

    @Test
    public void 동기화한_MAP (){

        Map<String, String> dummyMap = new HashMap<>();
        //given

        //when

        //then
    }
    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

package domain.ch11;

import java.util.concurrent.TimeUnit;

public class StopThread {

    private boolean stopRequest = false;
    private volatile boolean volatileStopRequest = false;

    public void 가시성_문제에_의한_영원히_동작하는_예시() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stopRequest) { // 동기화가 되어있지 않기때문에 가시성문제가 발생하여, 최종변경 결과를 확인할 수 없다.
                System.out.println(i++);
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        stopRequest = true; // 과연 쓰레드가 멈출까?
    }

    public void 동기화를통한_가시성문제_해결_예시() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!getStopRequest()) { // 동기화가 되어있어 최종 변경 상태를 가져온다.
                System.out.println(i++);
                sleepMilliSeconds(3);
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        requestStop(); // 과연 쓰레드가 멈출까?
    }

    private synchronized boolean getStopRequest() {
        return stopRequest;
    }

    private synchronized void requestStop() {
        stopRequest = true;
    }

    public void volatile_변수를_사용한_가시성문제해결_예시() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!volatileStopRequest) { // volatile 변수는 메인 메모리에 서 꺼내고 작성하기 때문에 가시성 문제가 해결된다.
                System.out.println(i++);
                sleepMilliSeconds(3);

            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        volatileStopRequest = true; // 과연 쓰레드가 멈출까?
    }

    private void sleepMilliSeconds(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

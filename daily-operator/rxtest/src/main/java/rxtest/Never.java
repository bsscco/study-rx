package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Never {

    public static void main(String[] args) {
        Observable
                .never()
                .subscribe(
                        item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item), // 호출되지 않습니다.
                        e -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()), // 호출되지 않습니다.
                        () -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted") // 호출되지 않습니다.
                );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
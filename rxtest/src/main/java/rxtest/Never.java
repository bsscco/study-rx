package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Never {

    public static void main(String[] args) {
        Observable
                .never()
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEach: " + notification))
                .subscribeOn(Schedulers.io())
                .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item)) // 호출되지 않습니다.
                .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted")) //  호출되지 않습니다.
                .doOnError(e -> System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage())) // 호출되지 않습니다.
                .subscribe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
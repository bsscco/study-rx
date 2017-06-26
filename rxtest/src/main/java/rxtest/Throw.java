package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Throw {

    public static void main(String[] args) {
        Observable
                .error(new Exception("에러입니다."))
                .subscribeOn(Schedulers.io())
                .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item)) // 호출되지 않습니다.
                .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted")) // 호출되지 않습니다.
                .doOnError(e -> System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()))
                .subscribe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
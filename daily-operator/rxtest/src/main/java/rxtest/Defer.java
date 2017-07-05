package rxtest;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.schedulers.Schedulers;

public class Defer {

    public static void main(String[] args) {
        Observable
                .defer(() -> {
                    return Observable
                            .range(1, 3)
                            .doOnEach(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tdoOnEach: " + item))
                            .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tdoOnNext: " + item))
                            .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tdoOnCompleted"))
                            .doOnError(e -> System.err.println("Thread:" + Thread.currentThread().getName() + "\tdoOnError: " + e.getMessage()));
                })
                .subscribe(
                        item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
                        e -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()),
                        () -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted")
                );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
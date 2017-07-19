package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;


public class Distinct {

    public static void main(String[] args) {
        Observable
                .just(0, 1, 1, 0, 2, 3, 1)
                .distinct()
                .observeOn(Schedulers.io())
                .subscribe(
                        (Integer item) -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
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
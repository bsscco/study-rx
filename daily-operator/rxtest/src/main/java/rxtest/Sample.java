package rxtest;

import rx.Observable;

import java.util.concurrent.TimeUnit;


public class Sample {

    public static void main(String[] args) {
        Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .sample(310, TimeUnit.MILLISECONDS)
                .subscribe(
                        /*
                        * 310초 -> 2
                        * 620초 -> 5
                        * 930초 -> 8
                        * ...
                        * */
                        (Long item) -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
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
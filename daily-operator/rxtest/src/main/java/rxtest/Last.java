package rxtest;

import rx.Observable;


public class Last {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .last((Integer item) -> item % 2 == 0)
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
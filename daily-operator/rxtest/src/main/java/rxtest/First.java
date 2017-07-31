package rxtest;

import rx.Observable;
import rx.functions.Func1;


public class First {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .first((Integer item) -> item % 2 == 0)
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
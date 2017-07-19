package rxtest;

import rx.Observable;

import java.lang.reflect.Array;
import java.util.regex.Pattern;


public class Window {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .window(2, 4)
                .subscribe(
                        (Observable<Integer> item) -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
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
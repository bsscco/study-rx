package rxtest;

import rx.Observable;


public class Window {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .window(2, 4)
                .subscribe(
                        item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item), // scan()의 첫인자였던 initialValue부터 발생됩니다.
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
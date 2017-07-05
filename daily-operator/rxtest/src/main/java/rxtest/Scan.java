package rxtest;

import rx.Observable;


public class Scan {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .scan(0, (accum, item) -> accum + item) // 인자로 누적된값과 아이템이 들어옵니다.
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
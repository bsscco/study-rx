package rxtest;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;


public class Debounce {

    public static void main(String[] args) {
        Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .filter((Long item) -> {
                    /*
                    (0 + 1) % 5 = 1
                    (1 + 1) % 5 = 2
                    (2 + 1) % 5 = 3
                    (3 + 1) % 5 = 4 (debounce에서 실제로 발생)
                    (4 + 1) % 5 = 0 (제외)
                    (5 + 1) % 5 = 1
                    (6 + 1) % 5 = 2
                    (7 + 1) % 5 = 3
                    (8 + 1) % 5 = 4 (debounce에서 실제로 발생)
                    (9 + 1) % 5 = 0 (제외)
                    (10 + 1) % 5 = 1
                    ...
                    * */
                    return (item + 1) % 5 != 0;
                })
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(
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
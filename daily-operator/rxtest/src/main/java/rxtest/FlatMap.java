package rxtest;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;

public class FlatMap {

    public static void main(String[] args) {
        Observable
                .range(1, 10)
                .buffer(2, 4) // 1,2번째는 발생시키고, 3,4번째는 스킵, 다시 5,6는 발생시킵니다.
                .flatMap(item -> Observable.from(item))
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
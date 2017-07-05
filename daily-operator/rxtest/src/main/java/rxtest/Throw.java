package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Throw {

    public static void main(String[] args) {
        Observable
                .error(new Exception("에러입니다."))
                .subscribe(
                        item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item), // 호출되지 않습니다.
                        (e) -> {
                            // doOnError는 에러를 통보받을 뿐이지 catch할 수는 없습니다.
                            // subscribe()에 onError콜백을 넣어주어야 threadException이 발생하지 않습니다.
                            System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage());
                        },
                        () -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted") // 호출되지 않습니다.
                );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package rxtest;

import rx.Observable;


public class ElementAt {

    public static void main(String[] args) {
        Observable
                .range(1, 5)
                .elementAt(2/*인덱스 2 -> 3번째만 방출*/)
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
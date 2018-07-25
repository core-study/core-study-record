package com.tmon.practice.hateoas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GreetingServiceTest {
    @Autowired
    GreetingService greetingService;

    @Autowired
    @Qualifier("asyncThreadPoolTaskExecutor")
    Executor taskExecutor;

    @Test
    public void rxJava01Test() {
        greetingService.rxTest01();
    }

    @Test
    public void rxJava02Test() throws InterruptedException {
        greetingService.rxTest02();
    }

    @Test
    public void rxJava03Test() {
        greetingService.rxTest03();
    }

    @Test
    public void rxJava04Test() {
        greetingService.rxTest04();
    }

    @Test
    public void rxJava05Test() {
        greetingService.rxTest05();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rxInitTest01() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { System.out.println(s); }
            @Override
            public void onCompleted() { }
            @Override
            public void onError(Throwable e) { }
        };

        myObservable.subscribe(mySubscriber);
    }

    @Test
    public void rxInitTest02() {

        ThreadPoolTaskExecutor subscribeExecutor = new ThreadPoolTaskExecutor();
        subscribeExecutor.setCorePoolSize(10);
        subscribeExecutor.setMaxPoolSize(10);
        subscribeExecutor.setQueueCapacity(10);
        subscribeExecutor.setThreadNamePrefix("SubscribeRxTestExecutor-");
        subscribeExecutor.initialize();

        List<Observable> observableList = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            observableList.add(Observable.fromCallable(() -> {
                Thread.sleep(100L);
                return "abc";
            }));
        }
        observableList.stream()
                .forEach(observable -> {
                    observable.doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                            .subscribeOn(Schedulers.from(taskExecutor)) // process
                            .observeOn(Schedulers.from(subscribeExecutor)) // subscribe
                            .subscribe(length -> System.out.println("item length " + length + " received on thread " + Thread.currentThread().getName()));
                });

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

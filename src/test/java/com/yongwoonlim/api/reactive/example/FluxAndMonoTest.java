package com.yongwoonlim.api.reactive.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.yongwoonlim.api.reactive.config.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluxAndMonoTest {
    @Mock
    CustomException customExceptionMono;
    @Mock
    CustomException customExceptionFlux;

    @BeforeEach
    void setup() {
        customExceptionFlux = new CustomException("Flux");
        customExceptionMono = new CustomException("Mono");
    }

    @DisplayName("Flux just() Sample")
    @Test
    void fluxJustTest() {
        List<String> names = new ArrayList<>();
        Flux<String> nameFlux = Flux.just("토니", "토르", "스티브").log();
        nameFlux.subscribe(names::add);
        assertThat(names, is(equalTo(Arrays.asList("토니", "토르", "스티브"))));
    }

    @DisplayName("Flux range() Sample")
    @Test
    void rangeTest() {
        List<Integer> list = new ArrayList<>();
        Flux<Integer> flux = Flux.range(1, 5).log();
        flux.subscribe(list::add);
        assertThat(list.size(), is(5));
        assertThat(list.get(2), is(3));
        assertThat(list.get(3), not(5));
    }

    @DisplayName("Flux fromArray() Sample")
    @Test
    void fromArrayTest() {
        List<String> list = new ArrayList<>();
        Flux<String> flux = Flux.fromArray(new String[]{"토니", "토르", "스티브"}).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("토니", "토르", "스티브"))));
    }

    @DisplayName("Flux fromIterable() Sample")
    @Test
    void fromIterableTest() {
        List<String> list = new ArrayList<>();
        Flux<String> flux = Flux.fromIterable(Arrays.asList("토니", "토르", "스티브")).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("토니", "토르", "스티브"))));
    }

    @DisplayName("Flux fromStream() Sample")
    @Test
    void fromStreamTest() {
        List<String> list = new ArrayList<>();
        Flux<String> flux = Flux.fromStream(Stream.of("토니", "토르", "스티브")).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("토니", "토르", "스티브"))));
    }

    @DisplayName("Flux generate() Sample")
    @Test
    void generateTest() {
        Flux<String> flux = Flux.generate(
                () -> 5,
                (state, sink) -> {
                    sink.next("3 X " + state + " = " + 3 * state);
                    if (state == 10) {
                        sink.complete();
                    }

                    return state + 1;
                });

        flux.subscribe(System.out::println);
    }

    @DisplayName("Flux create() Sample")
    @Test
    void createTest() {
        /**
         * Flux.create()와 배압
         * Subscriber로부터 요청이 왔을 때(FluxSink#onRequest) 데이터를 전송하거나(pull 방식)
         * Subscriber의 요청에 상관없이 데이터를 전송하거나(push 방식)
         * 두 방식 모두 Subscriber가 요청한 개수보다 더 많은 데이터를 발생할 수 있다.
         * 이 코드는 Subscriber가 요청한 개수보다 3개 데이터를 더 발생한다. 이 경우 어떻게 될까?
         * 기본적으로 Flux.create()로 생성한 Flux는 초과로 발생한 데이터를 버퍼에 보관한다.
         * 버퍼에 보관된 데이터는 다음에 Subscriber가 데이터를 요청할 때 전달된다.
         * 요청보다 발생한 데이터가 많을 때 선택할 수 있는 처리 방식은 다음과 같다.
         * IGNORE : Subscriber의 요청 무시하고 발생(Subscriber의 큐가 다 차면 IllegalStateException 발생)
         * ERROR : 익셉션(IllegalStateException) 발생
         * DROP : Subscriber가 데이터를 받을 준비가 안 되어 있으면 데이터 발생 누락
         * LATEST : 마지막 신호만 Subscriber에 전달
         * BUFFER : 버퍼에 저장했다가 Subscriber 요청시 전달. 버퍼 제한이 없으므로 OutOfMemoryError 발생 가능
         * Flux.create()의 두 번째 인자로 처리 방식을 전달하면 된다.
         * */

        Flux<Integer> flux = Flux.create((fluxSink) -> {
            fluxSink.onRequest(request -> {
                IntStream.range(1, 10).forEach(fluxSink::next);
            });
        });

        flux.log().subscribe(System.out::println);
    }

    @DisplayName("Flux empty() Sample")
    @Test
    void emptyTest() {
        List<String> list = new ArrayList<>();
        Flux<String> flux = Flux.empty();
        flux.subscribe(list::add);
        assertThat(list.size(), is(0));
    }

    @DisplayName("Mono just() Sample")
    @Test
    void monoJustTest() {
        /**
         * Reactive Stream 에서는 Data, Event, Signal 중에서 Signal 을 사용한다.
         * onNext, onComplete, onError
         * */

        List<Signal<Integer>> list = new ArrayList<>(4);
        final Integer[] results = new Integer[1];
        Mono<Integer> mono = Mono.just(1).log()
                .doOnEach(i -> {
                    list.add(i);
                    System.out.println("Signal : " + i);
                });
        mono.subscribe(i -> results[0] = i);
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getType().name(), is(equalTo("ON_NEXT")));
        assertThat(list.get(1).getType().name(), is(equalTo("ON_COMPLETE")));
        assertThat(results[0], is(1));
    }

    @DisplayName("Mono empty() Sample")
    @Test
    void monoEmptyTest() {
        Mono<String> result = Mono.empty();
        AtomicReference<String> value = new AtomicReference<>();
        result.log().subscribe(value::set);
        assertThat(value.get(), is(nullValue()));
    }

    @DisplayName("Mono just() Sample")
    @Test
    void monoJustTest2() {
        System.out.println("--------- Empty Mono ---------");
        Mono.empty().subscribe(System.out::println);
        System.out.println("--------- Mono.just() ---------");
        Mono.just("Java")
                .map(item -> "Mono item: " + item)
                .subscribe(System.out::println);
        System.out.println("--------- Empty Flux ---------");
        Flux.empty().subscribe(System.out::println);
        System.out.println("--------- Flux.just() ---------");
        Flux.just("Java", "Oracle", "Python")
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }

    @DisplayName("Mono Flux error() Sample")
    @Test
    void errorTest() {
        Mono.error(customExceptionMono)
                .doOnEach(e -> System.out.println("Mono inside doOnError"))
                .subscribe(System.out::println);

        Flux.error(customExceptionFlux)
                .doOnEach(e -> System.out.println("Flux inside doOnError"))
                .subscribe(System.out::println);
    }
}

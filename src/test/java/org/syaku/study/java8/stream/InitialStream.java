package org.syaku.study.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class InitialStream {
    @Test
    public void array() {
        String[] data = new String[]{ "가", "나" };
        List<String> result = Arrays.stream(data).collect(Collectors.toList());
        assertEquals(result, Arrays.asList(data));
    }

    @Test
    public void builder() {
        List<Integer> result = Stream

                // 스트림은 제너릭 타입을 사용하므로 값의 타입을 추론할 수 있도록 파라미터 타입을 설정했다.
                // 타입을 설정하지 않으면 기본적 타입은 Object 로 선언된다
                .<Integer>builder().add(1).add(2).build()
                // 결과를 얻는 다.
                .collect(Collectors.toList());

        assertEquals(result, asList(1,2));
    }

    @Test
    public void empty() {
        List<Integer> result = Stream.<Integer>empty().collect(Collectors.toList());
        assertEquals(result, Collections.emptyList());
    }

    @Test
    public void of() {
        // todo of 는 파라미터 타입을 선언하지 않아도 추론이 가능하다. why?
        List<String> kor = Stream.of("가").collect(Collectors.toList());
        List<Integer> num = Stream.of(1, 2, 3).collect(Collectors.toList());

        assertEquals(kor, Collections.singletonList("가"));
        assertEquals(num, asList(1,2, 3));
    }

    @Test
    public void iterate() {
        // todo 어디에 쓸까?
        List<String> result = Stream
                // 무한 반복이 된다. limit 를 이용하여 횟수를 설정한다.
                .iterate("안녕", n -> n).limit(2)
                .collect(Collectors.toList());
        assertEquals(result, asList("안녕", "안녕"));
    }

    @Test
    public void generate() {
        // todo 어디에 쓸까?
        List<String> result = Stream
                // Supplier 함수 인터페이스를 사용 - 인수 없고 결과 있음
                .generate(() -> "안녕")
                .limit(2).collect(Collectors.toList());
        assertEquals(result, asList("안녕", "안녕"));
    }

    @Test
    public void concat() {
        List<Integer> result = Stream.concat(Stream.of(1,2), Stream.of(3,4)).collect(Collectors.toList());
        assertEquals(result, asList(1,2,3,4));
    }
}

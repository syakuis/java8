package org.syaku.study.java8.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IntermediateStream {
    private List<String> color = Arrays.asList("Green", "Black", "Yellow", "Red", "Black", "Yellow");
    private List<Integer> number = Arrays.asList(4, 5, 1, 3, 6, 5, 1);

    @Test
    public void filter() {
        List<String> result =
                // stream() 컬랙션을 스트림으로 사용한다.
                color.stream()
                        // 필터를 이용하여 R 을 가진 데이터만 추출한다.
                        .filter(n -> n.contains("R"))
                        .collect(Collectors.toList());

        assertEquals(result, Collections.singletonList("Red"));
    }

    @Test
    public void map() {
        List<Integer> result = color.stream()
                // Function 함수 인터페이스를 사용하고 원하는 자료형의 데이터로 반환한다.
                // n -> n.length() 의 축약
                .map(String::length)
                .collect(Collectors.toList());

        assertEquals(result, Arrays.asList(5, 5, 6, 3, 5, 6));
    }

    @Test
    public void mapToInt() {
        // mapToInt 는 최종 데이터를 숫자로 반환하는 것을 목표하는 것 같다.
        // 컬랙션의 모든 합과 같은 최종 결과를 숫자로 표기할때 사용된다.
        int total = color.stream()
                // mapToInt 는 IntStream 으로 반환하며 IntStream 원시 자료형 int 로 반환한다.
                .mapToInt(String::length).sum();

        assertEquals(total, 30);
    }

    // mapToLong , mapToDouble 생략함

    @Test
    public void flatMap() {
        // 테스트
        // 2차 배열을 1차 배열로 만든다라는 설명으로 이해할 수 없었다.
        List<List<String>> list =
            Arrays.asList(Arrays.asList("a"), Arrays.asList("b", "c"));

        // 이해하기 위해 두가 코드로 작성하고 반환 자료형을 확인하였다.

        // map 과 flatMap 의 차이.
        // map 은 인자 그대로 반환하고 즉 아래처럼 스트림으로 만들면 스트림으로 반환된다.
        // flatMap 은 반환 자료형은 스트림의 제너릭 자료형으로 스트림의 값을 반환한다.
        list.stream()
                // n -> n.stream()
                .map(Collection::stream)
                // List<Stream<String>>
                .collect(Collectors.toList()).forEach(log::debug);
        List<String> result = list.stream().flatMap(Collection::stream)
                // List<String>
                .collect(Collectors.toList());
        result.forEach(log::debug);

        assertEquals(result, Arrays.asList("a", "b", "c"));
    }

    @Test
    public void flatMapToInt() {
        List<List<String>> list =
            Arrays.asList(Collections.singletonList("a"), Arrays.asList("b", "c"));

        int total = list.stream()
                // flatMapToInt 은 IntStream 으로 반환해야 한다. 즉 반환 자료형은 int 여야 한다.
                // mapToInt 를 이용하면 스트림의 값을 IntStream 으로 변환할 수 있다.
                .flatMapToInt(n -> n.stream().mapToInt(String::length)).sum();

        assertEquals(total, 3);
    }

    // flatMapToLong , flatMapToDouble 생략

    @Test
    public void distinct() {
        List<String> result = color.stream().distinct().collect(Collectors.toList());
        assertEquals(result, Arrays.asList("Green", "Black", "Yellow", "Red"));
    }

    @Test
    public void sorted() {
        List<Integer> result = number.stream().sorted().collect(Collectors.toList());
        assertEquals(result, Arrays.asList(1, 1, 3, 4, 5, 5, 6));

        List<Integer> result2 = number.stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList());
        assertEquals(result2, Arrays.asList(6, 5, 5, 4, 3, 1, 1));
        result.forEach(log::debug);
        result2.forEach(log::debug);
    }

    @Test
    public void peek() {
        // peek = void consumer(T s)
        List<String> result = color.stream().peek(s -> {
            assertTrue(color.indexOf(s) > -1);
        }).collect(Collectors.toList());
        assertEquals( result,color);
    }

    @Test
    public void limit() {
        List<String> result = color.stream().limit(2).collect(Collectors.toList());
        assertEquals(result, Arrays.asList("Green", "Black"));
    }

    @Test
    public void skip() {
        List<String> result = color.stream().skip(1).collect(Collectors.toList());
        assertEquals(result, Arrays.asList("Black", "Yellow", "Red", "Black", "Yellow"));
    }
}

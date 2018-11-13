package org.syaku.study.java8.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MiddleStream {
    private List<String> color = Arrays.asList("Green", "Black", "Yellow", "Red", "Black", "Yellow");
    private List<Integer> number = Arrays.asList(4, 5, 1, 3, 9, 5, 1);

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
                // Function 함수 인터페이스를 사용하고 원하는 타입의 데이터로 반환한다.
                // n -> n.length() 의 축약
                .map(String::length)
                .collect(Collectors.toList());

        assertEquals(result, Arrays.asList(5, 5, 6, 3, 5, 6));
    }

    @Test
    public void mapToInt() {
        // 이번 예는 올바르다고 할 수 없다.
        List<Integer> result = color.stream()
                // mapToInt 는 IntStream 으로 반환하며 IntStream 프리미티브 타입 int 로 반환한다.
                .mapToInt(String::length)
                // 박싱을 해야 컬랙션으로 만들 수 있다. List<int> 라는 건 존재하지 않으니까.
                .boxed()
                .collect(Collectors.toList());

        assertEquals(result, Arrays.asList(5, 5, 6, 3, 5, 6));

        // todo 올바른 예 만들기
    }

    // mapToLong , mapToDouble 생략함

    @Test
    public void flatMap() {
        /*List<Integer> result = color.stream()
                // Function 함수 인터페이스를 사용하고 원하는 데이터 타입의 스트림으로 반환한다.
                .flatMap(n -> n)
                .collect(Collectors.toList());*/
    }
}

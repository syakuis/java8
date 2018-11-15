package org.syaku.study.java8.stream;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.security.cert.CollectionCertStoreParameters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@Log4j2
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
        // 테스트
        // 2차 배열을 1차 배열로 만든다라는 설명으로 이해할 수 없었다.
        List<List<String>> list =
            Arrays.asList(Arrays.asList("a"), Arrays.asList("b", "c"));

        // 이해하기 위해 두가 코드로 작성하고 반환 유형을 확인하였다.

        // map 과 flatMap 의 차이.
        // map 은 인자 그대로 반환하고 즉 아래처럼 스트림으로 만들면 스트림으로 반환된다.
        // flatMap 은 반환 유형은 스트림의 제너릭 유형으로 스트림의 값을 반환한다.
        list.stream().map(n -> n.stream())
                // List<Stream<String>>
                .collect(Collectors.toList()).forEach(log::debug);
        List<String> result = list.stream().flatMap(n -> n.stream())
                // List<String>
                .collect(Collectors.toList());
        result.forEach(log::debug);

        assertEquals(result, Arrays.asList("a", "b", "c"));

    }
}

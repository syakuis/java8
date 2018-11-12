package org.syaku.study.java8.stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @since 12/11/2018
 */
@Log4j2
public class Streams {
  @Test
  public void create() {
    Arrays.stream(new String[]{ "가", "나", "다" });
    Arrays.asList("가", "나", "다").stream();
    Stream.of("가", "나", "다");
    Stream.<String>builder().add("가").add("나").add("다").build();
    // 가가가가가가가가가가
    Stream.generate(() -> "가").limit(10);
    // 가가가가가가가가가가
    Stream.iterate("가", v -> v).limit(10);

    // IntStream, LongStream, DoubleStream
    IntStream.range(1, 5);
    IntStream.rangeClosed(1, 5);
  }

  private List<String> data = Arrays.asList("Green", "Red", "Black", "Yellow");

  @Test
  public void filter() {
    List<String> result = data.stream().filter("Green"::equals).collect(Collectors.toList());
    result.forEach(n -> log.debug("{}", n));
  }

  @Test
  public void map() {
    List<Integer> result = data.stream().map(n -> n.length()).collect(Collectors.toList());
    result.forEach(n -> log.debug("{}", n));
  }

  @Test
  public void flatMap() {
    List<List<String>> list = Arrays.asList(Arrays.asList("a"), Arrays.asList("b"));
    List<String> list2 = list.stream().flatMap(Collection::stream).peek(log::debug).collect(Collectors.toList());
  }
}

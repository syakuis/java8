package org.syaku.study.java8.stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

import static org.junit.Assert.assertEquals;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @since 12/11/2018
 */
@Log4j2
public class BasicStream {
  @Test
  public void create() {

    Stream.of("가", "나", "다").forEach(log::debug);

    Stream.<String>builder().add("가").add("나").add("다").build().forEach(log::debug);

    Stream.generate(() -> "가").limit(10).forEach(log::debug);

    Stream.iterate("가", v -> v).limit(10).forEach(log::debug);

    Stream.concat(Stream.of(1, 2, 3), Stream.of(4, 5))
            .forEach(log::debug); // console.log

    Stream.empty();

    // IntStream, LongStream, DoubleStream 생략한다.
    IntStream.range(1, 5).forEach(log::debug);
    IntStream.rangeClosed(1, 5).forEach(log::debug);
  }

  private List<String> data = Arrays.asList("Green", "Red", "Black", "Yellow");

  @Test
  public void filter() {
    data.stream().filter("Green"::equals).collect(Collectors.toList()).forEach(log::debug);
  }

  @Test
  public void map() {
    data.stream().map(String::length).collect(Collectors.toList()).forEach(log::debug);
  }

  @Test
  public void flatMap() {
    List<List<String>> list = Arrays.asList(Arrays.asList("a", "aa"), Arrays.asList("b", "bb"));
    list.stream().flatMap(Collection::stream).forEach(log::debug);
  }

  @Test
  public void forEach() {
    Random random = new Random();
    random.ints().limit(10).forEach(log::debug);
  }

  @Test
  public void sorted() {
    IntStream.of(15,3,234,32,432,4,454,35,435).sorted().forEach(log::debug);
    // boxed 동작으로 Integer 로 박싱할 수 있다.
    IntStream.of(15,3,234,32,432,4,454,35,435).sorted().boxed().forEach(log::debug);
    // 내림차순 정렬 제너릭 유형을 사용하므로 원시형을 사용할 수 없어 박싱한다.
    IntStream.of(1,2,3,4,5).boxed().sorted(Comparator.reverseOrder()).forEach(log::debug);
  }

  @Test
  public void calculating() {
    assertEquals(IntStream.of(1,2,3,4).sum(), 10);
    assertEquals(IntStream.of(1,2,3,4).count(), 4);
    assertEquals(IntStream.of(1,2,3,4).max().getAsInt(), 4);
    assertEquals(IntStream.of(1,2,3,4).min().getAsInt(), 1);
    assertEquals(IntStream.of(1,2,3,4).average().getAsDouble(), 2.5, 2.5);
  }
}

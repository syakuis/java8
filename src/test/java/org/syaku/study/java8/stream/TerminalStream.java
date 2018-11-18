package org.syaku.study.java8.stream;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @since 18/11/2018
 */
@Log4j2
public class TerminalStream {
  private List<String> color = Arrays.asList("Green", "Black", "Yellow", "Red", "Black", "Yellow");
  private List<Integer> number = Arrays.asList(4, 5, 1, 3, 6, 5, 1);


  @Test
  public void forEach() {
    // peek 은 중간 연산이고 forEach 는 최종 연산이다.
    // peek 는 최종 연산을 하지 않으면 실행되지 않는 지연 연산에 속한다.
    color.forEach(s -> {
      log.debug(s);
      assertTrue(color.indexOf(s) > -1);
    });
  }

  @Test
  public void forEachOrdered() {
    // forEach 병렬 스트림에서 정렬을 보장받지 못한다. 보장받기 위해 forEachOrdered 를 사용해야 한다.
    color.forEach(s -> {
      log.debug(s);
      assertTrue(color.indexOf(s) > -1);
    });
  }

  @Test
  public void toArray() {
    String[] result = color.stream().toArray(String[]::new);
    assertArrayEquals(result, color.toArray());
  }

  @Test
  public void collect() {
    List<String> result = number.stream().map(String::valueOf).collect(Collectors.toList());
    for(int i = 0; i < number.size(); i++) {
      assertEquals(result.get(i), String.valueOf(number.get(i)));
    }
  }

  @Test
  public void min() {
    // orElse 는 null 인 경우 0 을 반환한다.
    int result = number.stream().min(Integer::compareTo).orElse(0);
    assertEquals(result, 1);
  }

  @Test
  public void max() {
    int result = number.stream().max(Integer::compareTo).orElse(0);
    assertEquals(result, 6);
  }

  @Test
  public void count() {
    long result = number.stream().count();
    assertEquals(result, 7);
  }

  @Test
  public void anyMatch() {
    boolean aTrue = number.stream().anyMatch(s -> s == 1);
    assertTrue(aTrue);

    boolean aFalse = number.stream().anyMatch(s -> s == 0);
    assertFalse(aFalse);
  }

  @Test
  public void allMatch() {
    // anyMatch 와 다름 점은 and 조건이다.
    boolean aFalse = number.stream().allMatch(s -> s == 1);
    assertFalse(aFalse);

    List<String> color = Arrays.asList("Green", "Black");
    boolean aTrue = color.stream().allMatch(s -> s.length() == 5);
    assertTrue(aTrue);
  }

  @Test
  public void noneMatch() {
    // allMatch 의 반대이다.
    boolean aTrue = color.stream().noneMatch(s -> s.equals("Gray"));
    assertTrue(aTrue);
  }

  @Test
  public void findFirst() {
    // 비어있지 않는 첫번째 값을 반환한다.
    String result = color.stream().filter(s -> s.equals("Black")).findFirst().orElse("none");
    assertEquals(result, "Black");
  }

  @Test
  public void findAny() {
    // 순서와 상관없이 일치하는 값을 하나 반환한다.
    String result = color.stream().filter(s -> s.equals("Black")).findFirst().orElse("none");
    assertEquals(result, "Black");
  }

  @Test
  public void reduce() {
    // 다양한 결과를 만들 수 있다.
    Map<String, Integer> result = color.stream().reduce(new HashMap<>(), (map, value) -> {
      map.put(value, value.length());
      return map;
    }, (map1, map2) -> {
      map1.putAll(map2);
      return map1;
    });

    color.forEach(s -> assertEquals(s.length(), result.get(s).intValue()));
  }
}

package org.syaku.study.java8.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.function.*;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @since 11/11/2018
 */
@Log4j2
public class Functional {
  @FunctionalInterface
  interface StringUtils {
    String concat(String value, String value2);
  }

  @Test
  public void 인터페이스() {
    StringUtils string = (v1, v2) -> v1 + v2;
    assertEquals(string.concat("Hello", " World!!!"), "Hello World!!!");
  }

  private String concat(String value, String value2, Function<String, String> fn) {
    return fn.apply(value + value2);
  }

  @Test
  public void 메서드() {
    assertEquals(concat("Hello", " World!!!", p -> p), "Hello World!!!");
  }

  @Test
  public void 직접() {
    Function<String, String> string = v -> v + " World!!!";
    assertEquals(string.apply("Hello"), "Hello World!!!");
  }

  @Test
  public void composeAndThen() {
    Function<Integer, Integer> one = v -> 10 * v;
    Function<Integer, Integer> two = v -> v * v;

    assertEquals(one.compose(two).apply(2).intValue(), 40);
    assertEquals(one.andThen(two).apply(2).intValue(), 400);
  }

  /**
   * 두개의 인수를 받고 반환하지 않는 다.
   */
  @Test
  public void biConsumer() {
    BiConsumer<String, String> func = (v1, v2) -> {
      assertEquals(v1, "a");
      assertEquals(v2, "b");
    };
    func.accept("a", "b");
  }

  /**
   * 두 개의 인수를 받고 결과를 생성한다.
   */
  @Test
  public void biFunction() {
    BiFunction<String, String, String> func = (v1, v2) -> v1 + v2;
    assertEquals(func.apply("syaku", "syaku"), "syakusyaku");
  }

  /**
   * 동일한 타입의 두 인수에 받고 동일한 타입의 결과를 생성한다.
   */
  @Test
  public void binaryOperator() {
    BinaryOperator<List<String>> func = (v, v2) -> {
      List<String> result = new ArrayList<>(v);
      result.addAll(v2);
      return result;
    };

    List<String> kr = Arrays.asList("가", "나");
    List<String> us = Arrays.asList("a", "b", "c");

    List<String> result = new ArrayList<>(kr);
    result.addAll(us);
    assertEquals(func.apply(kr, us), result);

    BinaryOperator<Integer> max = BinaryOperator.maxBy(Comparator.naturalOrder());
    assertEquals(max.apply(1, 2).intValue(), 2);

    BinaryOperator<Integer> min = BinaryOperator.minBy(Comparator.naturalOrder());
    assertEquals(min.apply(1, 2).intValue(), 1);
  }

  /**
   * 두개 인자를 받고 결과를 boolean 으로 생성한다.
   */
  @Test
  public void biPredicate() {
    BiPredicate<String, String> func = Objects::equals;
    assertFalse(func.test("A", "B"));
    assertTrue(func.test("A", "A"));
  }

  /**
   * boolean 을 생성한다.
   */
  @Test
  public void booleanSupplier() {
    BooleanSupplier func = () -> false;
    assertFalse(func.getAsBoolean());
  }
}

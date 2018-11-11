package org.syaku.study.java8.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.function.*;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;

/**
 * 인수가 두개인 경우
 *
 * - biConsumer: 원하는 유형 두개의 인수를 받고 결과는 없다.
 * - biFunction: 원하는 유형 두개의 인수를 받고 원하는 유형의 결과를 반환한다.
 * - binaryOperator: 원하는 유형 두개의 인수를 받고 결과를 반환한다. 단 인수와 결과의 유형이 모두 동일해야 한다.
 * - biPredicate: 원하는 유형 두개의 인수를 받고 결과를  boolean 으로 반환한다.
 *
 * 인수가 한개인 경우
 *
 * - Consumer: 원하는 유형 한개의 인수를 받고 결과는 없다.
 * - Function: 원하는 유형 한개의 인수를 받고 원하는 유형의 결과를 반환한다.
 * - UnaryOperator: 원하는 유형 한개의 인수를 받고 결과를 반환한다. 단 인수와 결과의 유형이 모두 동일해야 한다.
 * - Predicate: 원하는 유형 한개의 인수를 받고 결과를  boolean 으로 반환한다.
 *
 * 인수가 없는 경우
 * - supplier: 인수가 없고 결과를 boolean 으로 반환한다.
 *
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

  @Test
  public void biConsumer() {
    BiConsumer<String, String> func = (v1, v2) -> {
      assertEquals(v1, "a");
      assertEquals(v2, "b");
    };
    func.accept("a", "b");
  }

  @Test
  public void biFunction() {
    BiFunction<String, String, String> func = (v1, v2) -> v1 + v2;
    assertEquals(func.apply("syaku", "syaku"), "syakusyaku");
  }

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

  @Test
  public void biPredicate() {
    // (v, v2) -> Objects.equals(v, v2);
    BiPredicate<String, String> func = Objects::equals;
    assertFalse(func.test("A", "B"));
    assertTrue(func.test("A", "A"));
  }

  @Test
  public void booleanSupplier() {
    BooleanSupplier func = () -> {
      log.debug("실행됨.");
      return false;
    };
    assertFalse(func.getAsBoolean());

    // 매개변수 한개가 false 이므로 두개다 실행되지 않는 다.
    BiFunction<BooleanSupplier, BooleanSupplier, Boolean> bool = (v, v2) -> v.getAsBoolean() && v2.getAsBoolean();
    bool.apply(func, func);
  }

  @Test
  public void consumer() {
    Consumer<String> func = v -> assertEquals(v, "a");
    func.accept("a");
  }

  @Test
  public void doubleBinaryOperator() {
    DoubleBinaryOperator func = (v, v2) -> v + v2;
    assertEquals(func.applyAsDouble(1.1, 1.1), 2.2, 2.2);
  }

  @Test
  public void doubleConsumer() {
    DoubleConsumer func = v -> assertEquals(v, 1.1, 1.1);
    func.accept(1.1);
  }

  @Test
  public void doubleFunction() {
    // v -> String.valueOf(v)
    DoubleFunction<String> func = String::valueOf;
    assertEquals(func.apply(1.2), "1.2");
  }

  @Test
  public void doublePredicate() {
    DoublePredicate func = v -> v > 1.1;
    assertTrue(func.test(1.2));
  }

  @Test
  public void doubleSupplier() {
    DoubleSupplier func = () -> 1.1;
    assertEquals(func.getAsDouble(), 1.1, 1.1);
  }

  @Test
  public void doubleToIntFunction() {
    DoubleToIntFunction func = v -> new Double(v).intValue();
    assertEquals(func.applyAsInt(1.1), 1);
    assertEquals(func.applyAsInt(1.9), 1);
  }

  @Test
  public void doubleToLongFunction() {
    DoubleToLongFunction func = v -> new Double(v).longValue();
    assertEquals(func.applyAsLong(1.1), 1L);
    assertEquals(func.applyAsLong(1.9), 1L);
  }

  @Test
  public void doubleUnaryOperator() {
    DoubleUnaryOperator func = v -> v * 2;
    assertEquals(func.applyAsDouble(2.1), 4.2, 4.2);

    DoubleUnaryOperator func2 = v -> v * v;
    assertEquals(func.andThen(func2).applyAsDouble(5), 100, 100);
    assertEquals(func.compose(func2).applyAsDouble(5), 50, 50);
  }

  @Test
  public void function() {
    Function<String, String> func = v -> v + "a";
    assertEquals(func.apply("a"), "aa");

    Function<String, String> func2 = v -> v + v;
    // func2 call
    assertEquals(func.compose(func2).apply("b"), "bba");
    // func call
    assertEquals(func.andThen(func2).apply("b"), "baba");
  }

  // Int, Long, Obj Function 은 Double 유사하므로 생략한다.

  @Test
  public void predicate() {
    Predicate<String> func = v -> Objects.equals(v, "ok");
    assertTrue(func.test("ok"));
    // 부정
    assertFalse(func.negate().test("ok"));

    // v -> "ok".contains(v)
    Predicate<String> func2 = "aaaaa"::contains;
    assertFalse(func.and(func2).test("ok"));
    assertTrue(func.or(func2).test("ok"));
  }

  @Test
  public void supplier() {
    String a = null;
    Supplier<Boolean> func = () -> {
      log.debug("실행됨.");
      return a != null;
    };

    // 매개변수 한개가 false 이므로 두개다 실행되지 않는 다.
    BiFunction<Supplier, Supplier, Boolean> bool = (v, v2) -> (boolean) v.get() && (boolean) v2.get();
    bool.apply(func, func);
  }

  @Test
  public void unaryOperator() {
    UnaryOperator<String> func = v -> v + "a";
    assertEquals(func.apply("a"), "aa");
  }
}

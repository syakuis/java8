package org.syaku.study.java8.stream;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Log4j2
public class OptionalStream {
    @Test
    public void empty() {
        // isPresent 는 값의 존재유무를 반환한다.
        assertFalse(Optional.empty().isPresent());
    }

    @Test
    public void of() {
        assertTrue(Optional.of("syaku").isPresent());
    }

    @Test(expected = NoSuchElementException.class)
    public void get() {
        // NoSuchElementException
        Optional.empty().get();
        assertEquals("syaku", Optional.of("syaku").get());
    }

    @Test
    public void ofNullable() {
        assertFalse(Optional.ofNullable(null).isPresent());
        assertTrue(Optional.ofNullable("syaku").isPresent());
    }

    @Test
    public void isPresent() {
        assertFalse(Optional.empty().isPresent());
        assertTrue(Optional.of("syaku").isPresent());
        assertTrue(Optional.of("").isPresent());
    }

    @Test
    public void ifPresent() {
        // if (val != null) {} 이전에 사용했던 방식
        Consumer<String> func = s -> log.debug("null 이 아니다.");

        Optional.<String>empty()
                // s -> func.accept(s)
                .ifPresent(func);
        Optional.of("syaku").ifPresent(func);
    }

    @Test
    public void orElse() {
        assertEquals("", Optional.empty().orElse(""));
    }

    @Test
    public void orElseGet() {
        assertEquals("syaku", Optional.of("syaku").orElseGet(() -> "aa"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void orElseThrow() {
        Optional.empty().orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void filter() {
        List<String> data = Arrays.asList("ab", "bv", "aa");
        assertEquals(data,
                Optional.of(data).filter(s -> s.size() > -1).get());
    }
}

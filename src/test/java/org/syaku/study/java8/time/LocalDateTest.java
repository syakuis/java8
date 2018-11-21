package org.syaku.study.java8.time;


import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
public class LocalDateTest {

    @Test
    public void instance() {
        LocalDate localDate = LocalDate.now();
        log.debug(localDate);
    }

    @Test
    public void format() {
        LocalDate localDate = LocalDate.now();
        log.debug(localDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
    }

    @Test
    public void ymd() {
        LocalDate localDate = LocalDate.now();
        log.debug("{} 년 {} 월 {} 일({}) 올해 {} 일째 이번달 총 {}일",
                localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek(),
                localDate.getDayOfYear(),
                localDate.lengthOfMonth());
    }
}

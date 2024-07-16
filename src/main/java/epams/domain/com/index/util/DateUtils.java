package epams.domain.com.index.util;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DateUtils {
    public List<LocalDate> createTodayAndTomorrowList(LocalDate today, LocalDate tomorrow) {
        return Arrays.asList(today, tomorrow);
    }
}
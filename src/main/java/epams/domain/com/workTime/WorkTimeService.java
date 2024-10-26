package epams.domain.com.workTime;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K210058
 * @implNote 코드 service
 * @since 2024-10-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkTimeService {

    private final WorkTimeRepository workTimeRepository;

    public WorkTimeDTO findWorkTime(long empId, String localDate) {
        return workTimeRepository.findWorkTime(empId, localDate);
    }

}
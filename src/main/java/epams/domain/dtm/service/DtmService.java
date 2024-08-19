package epams.domain.dtm.service;

import epams.domain.dtm.repository.DtmRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/***
 * @author 140024
 * @implNote Service
 * @since 2024-06-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DtmService {

    /***
	 * @author 140024
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmRepository dtmRepository;

}

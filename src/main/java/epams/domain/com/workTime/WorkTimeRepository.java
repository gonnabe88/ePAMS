package epams.domain.com.workTime;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.stereotype.Repository;

import groovyjarjarantlr4.v4.codegen.model.dbg;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkTimeRepository {

    private final SqlSessionTemplate sql;

	public WorkTimeDTO findWorkTime(Long empId,String ymd){
        Map<String, Object>params = new HashMap<>();
        params.put("empId",empId);
        params.put("ymd",ymd);
        return sql.selectOne("WorkTime.findWorkTime",params);
    }
}
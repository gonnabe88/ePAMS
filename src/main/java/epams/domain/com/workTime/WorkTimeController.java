package epams.domain.com.workTime;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workTime/")
public class WorkTimeController {


    private final WorkTimeService workTimeService;

    @GetMapping("/findWorkTime")
    public WorkTimeDTO findWorkTime(@RequestParam("empno")Long empno,@RequestParam("ymd")String ymd){
        return workTimeService.findWorkTime(empno, ymd);
    }
    
}

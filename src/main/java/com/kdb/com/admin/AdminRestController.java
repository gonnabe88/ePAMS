package com.kdb.com.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdb.com.dto.CodeDTO;
import com.kdb.com.dto.HtmlDTO;
import com.kdb.com.dto.LogLoginDTO;
import com.kdb.com.entity.LogLoginEntity;
import com.kdb.com.entity.MemberEntity;
import com.kdb.com.service.CodeService;
import com.kdb.com.service.HtmlService;
import com.kdb.com.service.LogService;
import com.kdb.com.service.MemberDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {
	
	private final MemberDetailsService memberservice;
	private final HtmlService htmlservice;
	private final CodeService codeservice;
	private final LogService logService;
	
    @GetMapping("/html")
    public ResponseEntity<List<HtmlDTO>> searchAllHtml(Model model) throws Exception{
    	List<HtmlDTO> htmlDTOs = htmlservice.findAll();
        return ResponseEntity.ok(htmlDTOs);
    }
    
    @PostMapping("/html/save")
    public ResponseEntity<Map<String, String>> saveHtmls(@RequestBody Map<String, List<HtmlDTO>> payload) {
    	
        List<HtmlDTO> added = payload.get("added");
        List<HtmlDTO> changed = payload.get("changed");
        List<HtmlDTO> deleted = payload.get("deleted");
        htmlservice.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
    
    @GetMapping("/code")
    public ResponseEntity<List<CodeDTO>> searchAllCode(Model model) throws Exception{
    	List<CodeDTO> codeDTOs = codeservice.findAll();
        return ResponseEntity.ok(codeDTOs);
    }
    
    @PostMapping("/code/save")
    public ResponseEntity<Map<String, String>> saveCodes(@RequestBody Map<String, List<CodeDTO>> payload) {
    	
        List<CodeDTO> added = payload.get("added");
        List<CodeDTO> changed = payload.get("changed");
        List<CodeDTO> deleted = payload.get("deleted");
        codeservice.save(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
    
    @GetMapping("/login")
    public ResponseEntity<List<LogLoginDTO>> searchLogLoginLAll(Model model) throws Exception{
    	List<LogLoginDTO> logloginDTOs = logService.findAll();
        return ResponseEntity.ok(logloginDTOs);
    }
    
    @GetMapping("/member")
    public ResponseEntity<List<MemberEntity>> searchAll1(Model model) throws Exception{
    	List<MemberEntity> memberList = memberservice.findAll();
    	Map<String, Object> data = new HashMap<>();
    	data.put("data", memberList);
    	
    	// 마지막 페이지 및 데이터 설정
        Map<String, Object> response = new HashMap<>();
        //response.put("last_page", 10);
        response.put("data", memberList);
        
        return ResponseEntity.ok(memberList);
    }
    
    @PostMapping("/member/save")
    public ResponseEntity<Map<String, String>> saveMembers1(@RequestBody Map<String, List<MemberEntity>> payload) {
    	
        List<MemberEntity> added = payload.get("added");
        List<MemberEntity> changed = payload.get("changed");
        List<MemberEntity> deleted = payload.get("deleted");

        log.warn("added : "+ added.toString());
        log.warn("changed : "+ changed.toString());
        log.warn("deleted : "+ deleted.toString());
        
        memberservice.saveMembers(added, changed, deleted);

        return ResponseEntity.ok(Map.of("message", "Data saved successfully!"));
    }
    
}

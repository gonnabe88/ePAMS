package com.kdb.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String userId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

}

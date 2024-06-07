package com.kdb.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardImageDTO {
    private Long id;
    private Long boardId;
    private String originalFileName;
    private String storedFileName;
}

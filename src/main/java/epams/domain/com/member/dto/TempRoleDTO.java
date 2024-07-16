package epams.domain.com.member.dto;

/**
 * @author K140024
 * @implNote 사용자 역할을 나타내는 열거형 클래스
 * @since 2024-06-11
 */
public enum TempRoleDTO {
    
    /**
     * 관리자 역할
     */
    ROLE_ADMIN("ADMIN"),
    
    /**
     * 일반 사용자 역할
     */
    ROLE_NORMAL("NORMAL");

    /**
     * 역할 설명
     */
    private final String description;

    /**
     * 역할 설명으로 초기화하는 생성자
     * 
     * @param description 역할 설명
     */
    TempRoleDTO(final String description) {
        this.description = description;
    }
    
    /**
     * 역할 설명을 반환하는 메소드
     * 
     * @return 역할 설명
     */
    public String getDescription() {
        return description;
    }
}

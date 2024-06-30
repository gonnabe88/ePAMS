package epams.com.member.dto;

public enum TempRoleDTO {
    
    ROLE_ADMIN("ADMIN"),
    ROLE_NORMAL("NORMAL");

    private String description;

    TempRoleDTO(String description){
        this.description = description;
    }
}
package epams.com.member.entity;

/**
 * @author K140024
 * @category Entity Projection
 * @since 2024-04-28
 * 사용자 검색 시 화면에 보여줄 필요한 데이터만 프로젝션
 */
public interface SearchMemberEntity {
 
    String getUsername(); 
    String getDept();   
    String getTeam();   
    String getResponsibility();
    
}
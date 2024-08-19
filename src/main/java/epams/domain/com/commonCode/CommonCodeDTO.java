package epams.domain.com.commonCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pams.model.vo.CommonCodeVO;

/***
 * @author 140024
 * @implNote 근태 이력 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class CommonCodeDTO extends CommonCodeVO {

    /***
     * @author 140024
     * @implNote 코드유형(codeKind)을 인자로 받는 생성자
     * @since 2024-06-09
     */
    public CommonCodeDTO(String codeKind) {
        super();
        this.setCodeKind(codeKind);
    }

    /***
     * @author 140024
     * @implNote 코드유형(codeKind)과 코드(code)를 인자로 받는 생성자
     * @since 2024-06-09
     */
    public CommonCodeDTO(String codeKind, String code) {
        super();
        this.setCodeKind(codeKind);
        this.setCode(code);
    }
}

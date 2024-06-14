package com.kdb.com.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 모든 테이블이 공통으로 가져야할 컬럼을 정의하기 위한 entity
 * @since 2024-06-09
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class BaseEntity {
	
	/***
	 * @author 140024
	 * @implNote 생성시간
	 * @since 2024-06-09
	 */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

	/***
	 * @author 140024
	 * @implNote 업데이트시간
	 * @since 2024-06-09
	 */
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime;
}
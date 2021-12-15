package jp.co.ubinet.reserve.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "user_roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	  @Id
	    @Enumerated(EnumType.STRING) // ROLE_CODE
	    @Column(name = "role_code", length = 50, nullable = false, unique = true)
	    private RoleType type;
	  
	  @Column(name = "role_nm", length = 50, nullable = false)
	    private String name; //이름

	  @CreatedDate 
	    @Column(name = "regdate", updatable = false, nullable = false)
	    private LocalDateTime createdDate; // 생성 날짜
}

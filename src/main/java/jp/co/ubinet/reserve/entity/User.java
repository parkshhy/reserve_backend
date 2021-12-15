package jp.co.ubinet.reserve.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.*;

@Entity
@Table(name="user_tbl")
@Getter
@Setter
@Builder
@AllArgsConstructor // 모든 필드 생성자 자동 생성
@NoArgsConstructor // 파라미터가 없는 생성자 생성
public class User implements Serializable{

	private static final long serialVersionUID = -8630066657804966164L;

	@Id
	@Column(name = "user_id", length=10, nullable = false) //ユーザID
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	//@Column(name = "BOARD_SJ", length = 255, nullable = false)
	@Column(name ="user_name", length=255, nullable = false) //名前
	private String userName;
	
	@Column(name = "user_ruby", length=255, nullable = false) //ふりがな
	private String userRuby;
	
	@Column(name = "user_gender", length=10, nullable = false) //性別
	private String userGender;
	
	@Column(name = "user_tel_number" , length=30, nullable = false) //電話番号
	private String userTelNumber;
	
	@Column(name = "user_Post_number" , length=10, nullable = false) //郵便番号
	private String userPostNumber;
	
	@Column(name = "user_address" , length=255, nullable = false) //住所
	private String userAddress;
	
	@Column(name = "user_birthday") //誕生日
	private LocalDateTime userBirthday;
	
	@Column(name = "user_mail" , length=255, nullable = false) //メールアドレス
	private String userEmail;
	
	//password length ->255 
	@Column(name = "user_password" , length=50, nullable = false) //パスワード
	private String userPassword;
	
	@Column(name = "user_del_flg" , length=2, nullable = false) //削除フラグ
	private int userDelFlg;
	
	@Column(name = "user_place" , length=255, nullable = false) //学校名または企業名
	private String userPlace;
	
	@Column(name = "user_created_at") //作成日
	private LocalDateTime userCreatedAt;
	
	@Column(name = "user_updated_at") //更新日
	private LocalDateTime userUpdatedAt;
	
	@Column(name = "user_create_user", length=10, nullable = false) //作成者
	private int userCreateUser;
	
	// + Role Table 
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "role_code", referencedColumnName = "role_code")
	 private Role role;


}

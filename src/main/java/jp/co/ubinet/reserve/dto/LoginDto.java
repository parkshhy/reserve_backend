package jp.co.ubinet.reserve.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//로그인시 사용한다.
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

   @NotNull
   @Size(min = 3, max = 50)
   private String userName;

   @NotNull
   @Size(min = 3, max = 100)
   private String userPassword;
}

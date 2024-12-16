package online.quiz.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationLogin
{
    private String email;
    //private Long mobileNumber;
    private String password;
}

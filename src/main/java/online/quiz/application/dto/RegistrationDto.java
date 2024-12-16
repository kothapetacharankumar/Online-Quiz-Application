package online.quiz.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.quiz.application.enums.UserRoles;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto
{
    private Integer id;
    private String userName;
    private String email;
    private String createPassword;
    private String confirmPassword;
    private Long mobileNumber;
    private UserRoles userRoles;

}

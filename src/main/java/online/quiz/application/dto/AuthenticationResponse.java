package online.quiz.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.quiz.application.enums.UserRoles;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthenticationResponse
{

    private String jwt;
    private String emailId;
    private Integer userId;
    private UserRoles userRoles;
}

package online.quiz.application.entity.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.quiz.application.dto.RegistrationDto;
import online.quiz.application.enums.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistrationEntity implements UserDetails
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String userName;
    private String email;
    private String createPassword;
    private String confirmPassword;
    private Long mobileNumber;
    private UserRoles userRoles;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRoles.name()));
    }

    @Override
    public String getPassword()
    {
        if(createPassword.equals(confirmPassword))
            return confirmPassword;
        else
            return "confirm password and created password both are not same";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    public RegistrationDto getRegistrationDto()
    {
        RegistrationDto registrationDto=new RegistrationDto();
        registrationDto.setId(id);
        registrationDto.setEmail(email);
        registrationDto.setUserName(userName);
        registrationDto.setMobileNumber(mobileNumber);
        registrationDto.setCreatePassword(createPassword);
        registrationDto.setConfirmPassword(confirmPassword);
        registrationDto.setUserRoles(userRoles);
        return registrationDto;
    }

}

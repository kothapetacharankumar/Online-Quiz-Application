package online.quiz.application.servicesimplementation.auth;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import online.quiz.application.dto.RegistrationDto;
import online.quiz.application.entity.auth.RegistrationEntity;
import online.quiz.application.enums.UserRoles;
import online.quiz.application.repository.RegistrationRepository;
import online.quiz.application.services.jwt.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RequiredArgsConstructor
@Service
public class AuthServiceImplementation implements AuthService
{

    @Autowired
    private  RegistrationRepository registrationRepository;
 //  @PostConstruct
    public String createAnAdminAccount(RegistrationDto registrationDto)
    {
        if(registrationRepository.findFirstByEmail(registrationDto.getEmail()).isPresent())
        {
            throw new EntityExistsException("User already Present with this email" + registrationDto.getEmail());
        }


            if(registrationDto.getConfirmPassword().equals(registrationDto.getCreatePassword()))
            {
                String password = new BCryptPasswordEncoder().encode(registrationDto.getConfirmPassword());
                RegistrationEntity registrationEntity = new RegistrationEntity();
                registrationEntity.setEmail(registrationDto.getEmail());
                registrationEntity.setUserName(registrationDto.getUserName());
                registrationEntity.setUserRoles(UserRoles.ADMIN);
                registrationEntity.setConfirmPassword(password);
                registrationEntity.setCreatePassword(password);
                registrationEntity.setMobileNumber(registrationDto.getMobileNumber());
                registrationRepository.save(registrationEntity);
                System.out.println("Admin account created Successfully.");
                return "Admin Account created Successfully";
            }
            else {
                return "confirm password and created password both are not same, please enter correct password";

            }

    }

    public String createUser(RegistrationDto registrationDto)
    {
        if(registrationRepository.findFirstByEmail(registrationDto.getEmail()).isPresent())
        {
            throw new EntityExistsException("User already Present with this email" + registrationDto.getEmail());
        }

        RegistrationEntity registrationEntity=new RegistrationEntity();
        if(registrationDto.getCreatePassword().equals(registrationDto.getConfirmPassword()))
        {
            String password=new BCryptPasswordEncoder().encode(registrationDto.getConfirmPassword());
            registrationEntity.setUserName(registrationDto.getUserName());
            registrationEntity.setUserRoles(UserRoles.STUDENT);
            registrationEntity.setEmail(registrationDto.getEmail());
            registrationEntity.setConfirmPassword(password);
            registrationEntity.setCreatePassword(password);
            registrationEntity.setMobileNumber(registrationDto.getMobileNumber());
            RegistrationEntity createRegistration = registrationRepository.save(registrationEntity);
            return "Student account Successfully Registered";
        }else {
            return "confirm password and created password both are not same, please enter valid password";
        }
    }

}

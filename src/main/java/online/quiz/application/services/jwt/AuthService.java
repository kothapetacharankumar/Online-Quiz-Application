package online.quiz.application.services.jwt;

import online.quiz.application.dto.RegistrationDto;

public interface AuthService
{
    public String createAnAdminAccount(RegistrationDto registrationDto);
    public String createUser(RegistrationDto registrationDto);
}

package online.quiz.application.services.jwt;

import lombok.RequiredArgsConstructor;
import online.quiz.application.repository.RegistrationRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{

    private final RegistrationRepository registrationRepository;
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
            {
                return registrationRepository.findFirstByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
            }
        };
    }

}

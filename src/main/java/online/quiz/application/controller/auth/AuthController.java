package online.quiz.application.controller.auth;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import online.quiz.application.dto.AuthenticationLogin;
import online.quiz.application.dto.AuthenticationResponse;
import online.quiz.application.dto.RegistrationDto;
import online.quiz.application.entity.auth.RegistrationEntity;
import online.quiz.application.jwtutil.JwtUtil;
import online.quiz.application.repository.RegistrationRepository;
import online.quiz.application.services.jwt.AuthService;
import online.quiz.application.services.jwt.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class AuthController
{
    @Autowired
    private final AuthService service;

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final RegistrationRepository registrationRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserService userService;

    //admin registration
    @PostMapping("/admin/signup")
    public ResponseEntity<?> signupAdmin(@RequestBody RegistrationDto registrationDto)
    {
        try
        {
            String anAdminAccount = service.createAnAdminAccount(registrationDto);
            return new ResponseEntity<>(anAdminAccount,HttpStatus.OK);
        }
        catch (EntityExistsException e)
        {
            return new ResponseEntity<>("User already Existed", HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("User not created, Please come again later",HttpStatus.BAD_REQUEST);

        }

    }

    //student registration
    @PostMapping("/student/signup")
    public ResponseEntity<?> signupStudent(@RequestBody RegistrationDto registrationDto)
    {
        try
        {
            String createdUsers=service.createUser(registrationDto);
            return new ResponseEntity<>(createdUsers, HttpStatusCode.valueOf(200));
        }
        catch (EntityExistsException entityExistsException)
        {
            return new ResponseEntity<>("User already Existed", HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("User not created, Please come again later",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationLogin request)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        }
        catch(BadCredentialsException e)
        {
            throw new BadCredentialsException("Incorrect UserName or Password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());

        Optional<RegistrationEntity> users=registrationRepository.findFirstByEmail(userDetails.getUsername());

        final String jwt=jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(users.isPresent())
        {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRoles(users.get().getUserRoles());
            authenticationResponse.setUserId(users.get().getId());
            authenticationResponse.setEmailId(users.get().getEmail());
        }
        return authenticationResponse;


    }

}

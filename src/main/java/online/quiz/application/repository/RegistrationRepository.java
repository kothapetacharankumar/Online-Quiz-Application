package online.quiz.application.repository;

import online.quiz.application.entity.auth.RegistrationEntity;
import online.quiz.application.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Integer> {

    Optional<RegistrationEntity> findFirstByEmail(String email);

    Optional<RegistrationEntity> findByUserRoles(UserRoles userRole);
}

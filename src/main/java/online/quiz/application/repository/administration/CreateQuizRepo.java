package online.quiz.application.repository.administration;

import online.quiz.application.entity.administration.CreateQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface CreateQuizRepo extends JpaRepository<CreateQuiz, Integer> {
    Page<CreateQuiz> findByCategoryAndTopic(String category, String topic, Pageable pageable);
}

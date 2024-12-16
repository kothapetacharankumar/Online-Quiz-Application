package online.quiz.application.repository.student;

import online.quiz.application.entity.student.StudentQuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface StudentQuizProgressRepo  extends JpaRepository<StudentQuizProgress,Integer> {
    List<StudentQuizProgress> findByStudentId(Integer id);
}

package online.quiz.application.services.student;

import online.quiz.application.entity.administration.CreateQuiz;
import online.quiz.application.entity.student.StudentQuizProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StudentService
{
//    String registration(StudentRegistration studentRegistration);

    Page<CreateQuiz> getAllQuiz(String category, String topic, Pageable pageable);

    Double getScore(Integer id,Map<Integer, String> selectAnswer);

    List<StudentQuizProgress> getProgressofQuiz(Integer studentId);
}

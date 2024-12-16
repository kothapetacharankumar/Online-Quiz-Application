package online.quiz.application.services.administration;

import online.quiz.application.dto.CreateQuizDto;
import online.quiz.application.entity.administration.CreateQuiz;
import org.springframework.stereotype.Component;

@Component
public interface CreateQuizService {
    String createQuiz(CreateQuizDto createQuizDto);

    CreateQuiz getQuiz(Integer id);

    Boolean updateQuiz(Integer id,CreateQuizDto createQuizDto);

    String deleteQuiz(Integer id);


}

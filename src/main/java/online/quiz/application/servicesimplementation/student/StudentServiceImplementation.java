package online.quiz.application.servicesimplementation.student;

import online.quiz.application.entity.administration.CreateQuiz;
import online.quiz.application.entity.student.StudentQuizProgress;
import online.quiz.application.repository.administration.CreateQuizRepo;
import online.quiz.application.repository.student.StudentQuizProgressRepo;
import online.quiz.application.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService
{
    @Autowired
    CreateQuizRepo createQuizRepo;
    @Autowired
    StudentQuizProgressRepo studentQuizProgressRepo;

    //get All quiz questions and answers
    @Override
    public Page<CreateQuiz> getAllQuiz(String category, String topic, Pageable pageable)
    {
        return createQuizRepo.findByCategoryAndTopic(category.toLowerCase(),topic.toLowerCase(),pageable);
    }

    //get student quiz score

    @Override
    public Double getScore(Integer studentId,Map<Integer,String> selectAnswer)
    {
        //Total no of questions
        int countQuestions=selectAnswer.size();
        long countCorrectAnswers=selectAnswer.entrySet().stream().
                filter(entry->{
                    Integer id=entry.getKey();
                    String userAnswer=entry.getValue();
                    //fetch the correct answer  from the database
                    return createQuizRepo.findById(id)
                            .map(answer->answer.getAnswer().equalsIgnoreCase(userAnswer))
                            .orElse(false); // If the question ID doesn't exist, consider it incorrect
                }).count();
        //Calculate score as percentage
        Double percentage= (countCorrectAnswers/(double)countQuestions)*100;

        //store quiz progress in studentQuizProgress class or table
        Optional<Integer> id= selectAnswer.keySet().stream().findFirst().orElse(0).describeConstable();
        if(id.get()!=0) {
            Optional<CreateQuiz> create = createQuizRepo.findById(id.get());
            CreateQuiz createQuiz = create.get();
            StudentQuizProgress studentQuizProgress = new StudentQuizProgress();
            studentQuizProgress.setCategory(createQuiz.getCategory());
            studentQuizProgress.setTopic(createQuiz.getTopic());
            studentQuizProgress.setPercentage(percentage);
            studentQuizProgress.setSubmittedDate(new Date(System.currentTimeMillis()));
            studentQuizProgress.setStudentId(studentId);
            studentQuizProgressRepo.save(studentQuizProgress);

        }



        return percentage;
    }

    @Override
    public List<StudentQuizProgress> getProgressofQuiz(Integer studentId)
    {
        return studentQuizProgressRepo.findByStudentId(studentId);
    }
}

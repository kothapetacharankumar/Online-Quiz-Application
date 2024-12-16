package online.quiz.application.servicesimplementation.admin;

import online.quiz.application.dto.CreateQuizDto;
import online.quiz.application.entity.administration.CreateQuiz;
import online.quiz.application.repository.administration.CreateQuizRepo;
import online.quiz.application.services.administration.CreateQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateQuizServiceImple implements CreateQuizService
{
    @Autowired
    CreateQuizRepo createQuizRepo;

    @Override
    public String createQuiz(CreateQuizDto createQuizDto)
    {
        CreateQuiz create=new CreateQuiz();
        create.setAnswer(createQuizDto.getAnswer());
        create.setQuestion(createQuizDto.getQuestion());
        create.setChoice1(createQuizDto.getChoice1());
        create.setChoice2(createQuizDto.getChoice2());
        create.setChoice3(createQuizDto.getChoice3());
        create.setChoice4(createQuizDto.getChoice4());
        create.setCategory(createQuizDto.getCategory().toLowerCase());
        create.setTopic(createQuizDto.getTopic().toLowerCase());
        createQuizRepo.save(create);

        return "Successfully submitted";
    }

    @Override
    public CreateQuiz getQuiz(Integer id)
    {
        Optional<CreateQuiz> create=createQuizRepo.findById(id);
        if(create.isPresent())
        {
            CreateQuiz quiz=create.get();

            return quiz;
        }else {
            return null;
        }
    }

    @Override
    public Boolean updateQuiz(Integer id,CreateQuizDto createQuizDto)
    {
        Optional<CreateQuiz> byId = createQuizRepo.findById(id);
        if(byId.isPresent())
        {
            CreateQuiz create=byId.get();
            create.setChoice4(createQuizDto.getChoice4());
            create.setQuestion(createQuizDto.getQuestion());
            create.setChoice1(createQuizDto.getChoice1());
            create.setChoice2(createQuizDto.getChoice2());
            create.setChoice3(createQuizDto.getChoice3());
            create.setTopic(createQuizDto.getTopic().toLowerCase());
            create.setCategory(createQuizDto.getCategory().toLowerCase());
            create.setAnswer(createQuizDto.getAnswer());

            createQuizRepo.save(create);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String deleteQuiz(Integer id)
    {
        if(createQuizRepo.findById(id).isPresent()) {
            createQuizRepo.deleteById(id);
            return "Deleted Successfully";
        }
        else {
            return "Can't delete quiz, Please check ID";
        }
    }






}


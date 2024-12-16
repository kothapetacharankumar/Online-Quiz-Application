package online.quiz.application.controller.administration;

import online.quiz.application.dto.CreateQuizDto;
import online.quiz.application.entity.administration.CreateQuiz;
import online.quiz.application.services.administration.CreateQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AdministrationController
{

    @Autowired
    private CreateQuizService createQuizService;



    //createQuiz
    @PostMapping("/quiz")
    public ResponseEntity<String> createQuiz(@RequestBody CreateQuizDto createQuizDto)
    {
        String result=createQuizService.createQuiz(createQuizDto);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<CreateQuiz> getQuizId(@PathVariable Integer id) {
        CreateQuiz createQuiz = createQuizService.getQuiz(id);
        if (createQuiz != null) {
            return new ResponseEntity<>(createQuiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createQuiz, HttpStatus.NOT_FOUND);
        }

    }

    //update Quiz
    @PutMapping("/updateQuiz/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Integer id, @RequestBody CreateQuizDto createQuizDto)
    {
        Boolean success=createQuizService.updateQuiz(id,createQuizDto);
        if(success) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(success,HttpStatus.NOT_FOUND);
        }
    }

    //delete quiz based on id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Integer id)
    {
        String delete=createQuizService.deleteQuiz(id);
        if(delete.startsWith("Deleted"))
        {
            return new ResponseEntity<>(delete,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(delete, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}


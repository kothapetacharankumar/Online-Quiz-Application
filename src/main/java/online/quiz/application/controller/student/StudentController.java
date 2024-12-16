package online.quiz.application.controller.student;

import online.quiz.application.entity.administration.CreateQuiz;
import online.quiz.application.entity.student.StudentQuizProgress;
import online.quiz.application.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class StudentController
{

    @Autowired
    StudentService studentService;

    //get quiz
    @GetMapping("/getAllQuiz/{category}/{topic}")
    public ResponseEntity<Page<CreateQuiz>> getAllQuiz(@PathVariable String category, @PathVariable String topic,@RequestParam(defaultValue = "1") Integer pageNumber  )
    {
        Pageable pageable= PageRequest.of(pageNumber-1,1);
        Page<CreateQuiz> getAll=studentService.getAllQuiz(category,topic,pageable);
        if(getAll.hasContent()) {
            return new ResponseEntity<>(getAll,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(getAll,HttpStatus.NOT_FOUND);
        }
    }

    //scoring of student
    @GetMapping("/score/{id}")
    public ResponseEntity<?> getScore(@PathVariable Integer id,@RequestBody Map<Integer,String> selectAnswer)
    {
        Double score=studentService.getScore(id,selectAnswer);
        Map<String,Object> response=new HashMap<>();
        response.put("Your score",score);
        response.put("Total Questions :",selectAnswer.size());
        response.put("message","Successfully Calculated Score");
        return ResponseEntity.ok(response);
    }

    //student quiz progress
    @GetMapping("/progress")
    public ResponseEntity<?> getProgress(@RequestParam  Integer studentId)
    {
        List<StudentQuizProgress> progress=studentService.getProgressofQuiz(studentId);
        if(progress.isEmpty())
        {
            return ResponseEntity.ofNullable(progress);
        }else {
        return ResponseEntity.ok(progress);}
    }
}

package br.com.battcompany.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser);

        var currentDate = LocalDateTime.now(); // variavel que armazena em tempo real
        // 10/11/2023 - Current
        // 10/10/2023 - CreatedAT
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){ //Verifica se o usuario preencheu uma data anterior a data atual
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de ínicio / data de término deve ser maior do que a data atual!");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){ //Verifica se o usuario preencheu uma data anterior a data atual
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de ínicio deve ser menor doque a data de término!");
        }
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
 
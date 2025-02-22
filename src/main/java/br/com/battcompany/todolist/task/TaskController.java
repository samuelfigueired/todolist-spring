package br.com.battcompany.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import br.com.battcompany.todolist.utils.Utils;


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

    @GetMapping("/") //Listar todas as tarefas
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }
    //http//localhost:8080/tasks//5628-452csdvds
    @PutMapping("/{id}") //Podendo atualizar uma tarefa
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){ //Http servelet pois precisamos que o usuario esteja autenticadp
        //update
        var task = this.taskRepository.findById(id).orElse(null); //puxar nno banco de dados
        
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada!");
        }   

        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){ //Verifica se o usuario é o mesmo que criou a tarefa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não autorizado a alterar a tarefa!");
        }
        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.save(taskUpdated));
    }
}
 
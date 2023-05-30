package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.TaskModel;
import comixobit.SRL.FERMA.DE.VACI.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.config.Task;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminPanel/tasks")
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{idTask}")
    public TaskModel getTaskById(@PathVariable int idTask){
        return taskService.findById(idTask);
    }
}

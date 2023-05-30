package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.*;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmailService emailService;

    public TaskModel editTaskToDoToInprogress(int idTask){
        return taskRepository.updateStatusTaskToDoToInProgress(idTask);
    }

    public void editTaskInProgressToToDo(int idTask){
         taskRepository.updateStatusTaskInProgressToToDo(idTask);
    }

    public void editTaskInProgressToDone(int idTask){
         taskRepository.updateStatusTaskInProgressToDone(idTask);
    }

    public void editTaskDoneToInProgress(int idTask){
        taskRepository.updateStatusTaskDoneToInProgress(idTask);
    }

    public Page<TaskModel> getTasks(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return taskRepository.getListOfTasks(pageable);
    }

    public Page<TaskModel> getTasksInProgress(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return taskRepository.getListOfTasksInProgress(pageable);
    }

    public Page<TaskModel> getTasksDone(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return taskRepository.getListOfTasksDone(pageable);
    }

    public TaskModel saveTask(TaskModel taskModel,
                              String idnp){
        LucratorModel lucratorModel = employeeRepository.findByIdnp(idnp);

        taskModel.setLucratorModel(lucratorModel);

        String email = taskModel.getLucratorModel().getEmail();
        String subject = "Salut, " + taskModel.getLucratorModel().getNume() + " " + taskModel.getLucratorModel().getPrenume() + " ai un task nou. Revizuieste-l!";
        String message = "Salut, " + taskModel.getLucratorModel().getNume() + " " + taskModel.getLucratorModel().getPrenume() + " ai un task nou. Revizuieste-l!\n" + taskModel.getDescriere();

        emailService.sendEmail(email, subject, message);
        return taskRepository.save(taskModel);
    }

    public TaskModel findById(Integer id) {
        return taskRepository.getOne(id);
    }

    public void deleteById(Integer id){
        taskRepository.deleteById(id);
    }


}

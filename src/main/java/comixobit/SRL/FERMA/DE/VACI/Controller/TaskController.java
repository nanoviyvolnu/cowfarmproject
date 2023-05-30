package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Models.TaskModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.TaskRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/adminPanel/tasks/pagina/1")
    public String getAllPages(Model model){
        return getOnePageTask(model, 1);
    }

    @GetMapping("/adminPanel/tasks/pagina/{pageNumber}")
    private String getOnePageTask(Model model,
                                  @PathVariable("pageNumber") int currentPage) {
        Page<TaskModel> page = taskService.getTasks(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<TaskModel> taskModelList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("taskModelList", taskModelList);
        return "taskManagement/taskBoard";
    }

    @GetMapping("/adminPanel/tasks/inProgress/pagina/1")
    public String getAllPagesInProgressTask(Model model){
        return getOnePageTaskInProgress(model, 1);
    }

    @GetMapping("/adminPanel/tasks/inProgress/pagina/{pageNumber}")
    private String getOnePageTaskInProgress(Model model,
                                  @PathVariable("pageNumber") int currentPage) {
        Page<TaskModel> page = taskService.getTasksInProgress(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<TaskModel> taskModelList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("taskModelList", taskModelList);
        return "taskManagement/taskBoardInProcess";
    }

    @GetMapping("/adminPanel/tasks/done/pagina/1")
    public String getAllPagesDoneTask(Model model){
        return getOnePageTaskDone(model, 1);
    }

    @GetMapping("/adminPanel/tasks/done/pagina/{pageNumber}")
    private String getOnePageTaskDone(Model model,
                                            @PathVariable("pageNumber") int currentPage) {
        Page<TaskModel> page = taskService.getTasksDone(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<TaskModel> taskModelList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("taskModelList", taskModelList);
        return "taskManagement/taskBoardDone";
    }

    @GetMapping("/adminPanel/tasks/adaugaTask")
    private String createTaskForm(Model model, TaskModel taskModel) {
        List<String> idnpEmployers = taskRepository.employers();
        model.addAttribute("idnpEmployers", idnpEmployers);
        return "taskManagement/createTask";
    }

    @PostMapping("/adminPanel/tasks/adaugaTask/save")
    private String createTasks(TaskModel taskModel,
                               LucratorModel lucratorModel,
                               @RequestParam("idnp") String idnp,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "taskManagement/createTask";
        }
        taskService.saveTask(taskModel, idnp);
        return "redirect:/adminPanel/tasks";
    }

    @GetMapping("/adminPanel/task/stergeTask/pagina/{pageNumber}/{idTask}")
    private String deleteTask(@PathVariable("idTask") int idTask,
                              @PathVariable("pageNumber") int currentPage,
                              Model model) {
        Page<TaskModel> page = taskService.getTasks(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        taskService.deleteById(idTask);
        return "redirect:/adminPanel/tasks";
    }

    @GetMapping("/adminPanel/task/editeazaTask/pagina/{pageNumber}/{idTask}")
    private String editTask(@PathVariable("idTask") int idTask,
                            @PathVariable("pageNumber") int currentPage,
                            Model model) {
        TaskModel taskModel = taskService.findById(idTask);
        Page<TaskModel> page = taskService.getTasks(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("taskModel", taskModel);
        List<String> idnpEmployers = taskRepository.employers();
        model.addAttribute("idnpEmployers", idnpEmployers);

        return "taskManagement/updateTask";
    }

    @PostMapping("/adminPanel/task/editeazaTask/save")
    private String editeazaTaskSave(Model model,
                                    TaskModel taskModel,
                                    @RequestParam("idnp") String idnp,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "taskManagement/createTask";
        }
        taskService.saveTask(taskModel, idnp);
        return "redirect:/adminPanel/tasks";
    }

    @RequestMapping(value = "/adminPanel/tasks/toInProgress/pagina/{pageNumber}/{idTask}", method = { RequestMethod.GET, RequestMethod.POST })
    public String editToDoTask(@PathVariable("idTask") int idTask,
                               @PathVariable("pageNumber") int currentPage,
                               Model model) {
        TaskModel taskModel = taskService.findById(idTask);
        Page<TaskModel> page = taskService.getTasksInProgress(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        taskService.editTaskToDoToInprogress(idTask);
        return "redirect:/adminPanel/tasks/pagina/1";
    }

    @PostMapping("/adminPanel/tasks/inProgress/toToDo/pagina/{pageNumber}/{idTask}")
    public String editInProgressTaskToToDo(@PathVariable("idTask") int idTask,
                                           @PathVariable("pageNumber") int currentPage,
                                           Model model){
        Page<TaskModel> page = taskService.getTasks(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        taskService.editTaskInProgressToToDo(idTask);
        return "taskManagement/taskBoard";
    }

    @PostMapping("/adminPanel/tasks/inProgress/toDone/pagina/{pageNumber}/{idTask}")
    public String editInProgressTaskToDone(@PathVariable("idTask") int idTask,
                                           @PathVariable("pageNumber") int currentPage,
                                           Model model){
        Page<TaskModel> page = taskService.getTasksDone(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        taskService.editTaskInProgressToDone(idTask);
        return "taskManagement/taskBoardDone";
    }

    @PostMapping("/adminPanel/tasks/done/toInProgress/pagina/{pageNumber}/{idTask}")
    public String editDoneTaskToInProgress(@PathVariable("idTask") int idTask,
                                           @PathVariable("pageNumber") int currentPage,
                                           Model model){
        Page<TaskModel> page = taskService.getTasks(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        taskService.editTaskDoneToInProgress(idTask);
        return "taskManagement/taskBoardInProcess";
    }

}
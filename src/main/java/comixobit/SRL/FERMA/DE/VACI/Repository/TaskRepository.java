package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.TaskModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {
    @Query(value = "SELECT DISTINCT idnp FROM lucrator", nativeQuery = true)
    List<String> employers();

    @Query(value = "UPDATE task SET Status_task = 'IN PROGRESS' WHERE Id_task = :idTask", nativeQuery = true)
    TaskModel updateStatusTaskToDoToInProgress(@Param(value = "idTask") int idTask);
    @Query(value = "UPDATE task SET Status_task = 'TO DO' WHERE Id_task = :idTask", nativeQuery = true)
    void updateStatusTaskInProgressToToDo(int idTask);

    @Query(value = "UPDATE task SET Status_task = 'DONE' WHERE Id_task = :idTask", nativeQuery = true)
    void updateStatusTaskInProgressToDone(int idTask);

    @Query(value = "UPDATE task SET Status_task = 'IN PROGRESS' WHERE Id_task = :idTask", nativeQuery = true)
    void updateStatusTaskDoneToInProgress(int idTask);


    @Query(value = "SELECT task.Id_task, task.Denumire, task.Data_creare, task.Data_limita, task.Status_task, task.Descriere,\n" +
            "lucrator.nume, lucrator.prenume, lucrator.Id_lucrator\n" +
            "FROM task\n" +
            "INNER JOIN lucrator ON lucrator.Id_lucrator = task.Id_lucrator;", nativeQuery = true)
    Page<TaskModel> getListOfTasks(Pageable pageable);


    @Query(value = "SELECT task.Id_task, task.Denumire, task.Data_creare, task.Data_limita, task.Status_task, task.Descriere,\n" +
            "lucrator.nume, lucrator.prenume, lucrator.Id_lucrator\n" +
            "FROM task\n" +
            "INNER JOIN lucrator ON lucrator.Id_lucrator = task.Id_lucrator WHERE Status_task = 'IN PROGRESS';", nativeQuery = true)
    Page<TaskModel> getListOfTasksInProgress(Pageable pageable);

    @Query(value = "SELECT task.Id_task, task.Denumire, task.Data_creare, task.Data_limita, task.Status_task, task.Descriere,\n" +
            "lucrator.nume, lucrator.prenume, lucrator.Id_lucrator\n" +
            "FROM task\n" +
            "INNER JOIN lucrator ON lucrator.Id_lucrator = task.Id_lucrator WHERE Status_task = 'DONE';", nativeQuery = true)
    Page<TaskModel> getListOfTasksDone(Pageable pageable);
}

package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Models.TaskModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<LucratorModel> selectAllEmployee(){
        LucratorModel lucratorModel = new LucratorModel();
        return employeeRepository.findAll();
    }

    public Page<LucratorModel> findPage(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return employeeRepository.findAll(pageable);
    }

    public LucratorModel findById(Integer id){
        return employeeRepository.getOne(id);
    }

    public LucratorModel saveEmployee(LucratorModel lucratorModel){
        int totalSalary = lucratorModel.getRemunerarePeOra() * lucratorModel.getNormaDeMunca();
        lucratorModel.setSalariu(totalSalary);
        lucratorModel.setStatus("ACTIV");
        return  employeeRepository.save(lucratorModel);
    }

    public LucratorModel updateEmployee(LucratorModel lucratorModel){
        int totalSalary = lucratorModel.getRemunerarePeOra() * lucratorModel.getNormaDeMunca();
        lucratorModel.setSalariu(totalSalary);
        return  employeeRepository.save(lucratorModel);
    }

    public void deleteById(Integer id){
        employeeRepository.deleteById(id);
    }

    public LucratorModel findByName(Integer id){
        return employeeRepository.getOne(id);
    }

    public boolean findByIdnp(String idnp){
        return employeeRepository.existsByIdnp(idnp);
    }

    public int selectCountEmployers(){
        return employeeRepository.countEmployers();
    }


}

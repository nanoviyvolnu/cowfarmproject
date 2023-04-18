package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<LucratorModel> selectAllEmployee(){
        return employeeRepository.findAll();
    }

    public LucratorModel findById(Integer id){
        return employeeRepository.getOne(id);
    }

    public LucratorModel saveEmployee(LucratorModel lucratorModel){
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


}
package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CowService {
    private final CowRepository cowRepository;

    public CowService(CowRepository cowRepository) {
        this.cowRepository = cowRepository;
    }

    public List<VacaModel> selectAllCows(){
        return cowRepository.findAll();
    }

    public VacaModel findById(Integer id){
        return cowRepository.getOne(id);
    }

    public VacaModel saveCow(VacaModel vacaModel){
        return  cowRepository.save(vacaModel);
    }

    public void deleteById(Integer id){
        cowRepository.deleteById(id);
    }

    public VacaModel findByName(Integer id){
        return cowRepository.getOne(id);
    }
}

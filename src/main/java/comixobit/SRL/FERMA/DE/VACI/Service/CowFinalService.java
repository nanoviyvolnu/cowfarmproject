package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.*;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowFinalRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CowFinalService {

    @Autowired
    private CowFinalRepository cowFinalRepository;
    @Autowired
    private CowRepository cowRepository;

    @Transactional
    public void saveDiedCow(VacaModel vacaModel){

        VacaFinalModel vacaFinalModel = new VacaFinalModel();

        vacaModel = cowRepository.save(vacaModel);

        vacaFinalModel.setVacaModel(vacaModel);
        cowFinalRepository.save(vacaFinalModel);
    }

    public List<VacaFinalModel> selectAllDiedCows(){
        VacaFinalModel vacaFinalModel = new VacaFinalModel();
        return cowFinalRepository.getAllDiedCows();
    }

    public Page<VacaFinalModel> findPageByDiedCows(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return cowFinalRepository.getPageAllDiedCows(pageable);
    }

    public Page<VacaFinalModel> findDiedCowByRasa(@Param("rasa") String rasa,
                                                  Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return cowFinalRepository.findByRasaDiedCow(rasa, pageable);
    }

    public List<VacaFinalModel> diedCows(){
        return cowFinalRepository.getAllDiedCowsToPDF();
    }

    public List<VacaFinalModel> soldedCows(){
        return cowFinalRepository.getAllSoldedCowsToPDF();
    }
}

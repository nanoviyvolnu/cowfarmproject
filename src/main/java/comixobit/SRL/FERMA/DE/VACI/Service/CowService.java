package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaFinalModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Specification.CowSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CowService {
    private final CowRepository cowRepository;

    public CowService(CowRepository cowRepository) {
        this.cowRepository = cowRepository;
    }

    public Page<VacaModel> findByRasaCow(@Param("rasa") String rasa, int pageNumber){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return cowRepository.findByRasa(rasa, pageable);
    }

    public Page<VacaModel> findByRasaPregnatCow(@Param("rasa") String rasa, int pageNumber){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return cowRepository.findByRasaPregnatCows(rasa, pageable);
    }

    public Page<VacaModel> findBySickRasaCow(@Param("rasa") String rasa, int pageNumber){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return cowRepository.findByRasaSickCows(rasa, pageable);
    }



    public List<VacaModel> selectAllCows(){
        VacaModel vacaModel = new VacaModel();
        return cowRepository.findAll();
    }

    public Page<VacaModel> findPage(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return cowRepository.listActiveCows(pageable);
    }

    public Page<VacaModel> findPagePregnatCows(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return cowRepository.listPregnatCows(pageable);
    }

    public Page<VacaModel> findPageSickCows(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return cowRepository.listSickCows(pageable);
    }

    public List<VacaModel> sickCows(){
        return cowRepository.listSickCowsToPDF();
    }

    public List<VacaModel> activeCows(){
        return cowRepository.listActiveCowsToPDF();
    }

    public List<VacaModel> pregnatCows(){
        return cowRepository.listPregnatCowsToPDF();
    }


    public VacaModel findById(Integer id){
        return cowRepository.getOne(id);
    }

    public VacaModel saveCow(VacaModel vacaModel){
        if(String.valueOf(vacaModel.getPretul()) == null){
            vacaModel.setPretul(0);
        }
        return  cowRepository.save(vacaModel);
    }

    public void updateById(VacaModel vacaModel, int idVaca){
        if(String.valueOf(vacaModel.getPretul()) == null){
            vacaModel.setPretul(0);
        }
        cowRepository.save(vacaModel);
    }

    public void deleteById(Integer id){
        cowRepository.deleteById(id);
    }


    public List<String> findByRasa(String rasa) {
        return cowRepository.searchDistinctByRasa(rasa);
    }


    public List<String> findByCategorie(String categorie){
        return cowRepository.searchDistinctByCategorie(categorie);
    }

    public List<String> findByFormaDeAchizite(String formaDeAchizitie){
        return cowRepository.searchDistinctByFormaAchizitie(formaDeAchizitie);
    }

    public int selectCountCows(){
        return (int) cowRepository.count();
    }

    public List<String> findByStatut(String statutul){
        return cowRepository.searchDistinctByStatutul(statutul);
    }


    public List<VacaModel> findCowsByFilter(String rasa,
                                            String genul,
                                            String statutul,
                                            String formaDeAchizite,
                                            String categorie,
                                            String operator,
                                            String operator1,
                                            Integer masaKgInitiala,
                                            Integer masaKgMoment){

        Specification<VacaModel> specification = Specification.where(null);

        if(rasa != ""){
            specification = specification.and(CowSpecification.hasRasa(rasa));
        }

        if(genul != ""){
            specification  = specification.and(CowSpecification.hasGenul(genul));
        }

        if(statutul != ""){

            specification = specification.and(CowSpecification.hasStatutul(statutul));
        }

        if(formaDeAchizite != ""){
            specification = specification.and(CowSpecification.hasFormaDeAchizitie(formaDeAchizite));
        }

        if(categorie != ""){
            specification  = specification.and(CowSpecification.hasCategorie(categorie));
        }


        if (operator != "" && masaKgInitiala != 0) {
            specification = specification.and(CowSpecification.hasInitialWeight(operator, masaKgInitiala));
        }


        if (operator1 != "" && masaKgMoment != 0) {
            specification = specification.and(CowSpecification.hasMomentWeight(operator1, masaKgMoment));
        }



        return cowRepository.findAll(specification);
    }
}

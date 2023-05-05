package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Specification.CowSpecification;
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

    public List<VacaModel> findByRasaCow(@Param("rasa") String rasa){
        return cowRepository.findByRasa(rasa);
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

    public void updateById(VacaModel vacaModel, int idVaca){
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

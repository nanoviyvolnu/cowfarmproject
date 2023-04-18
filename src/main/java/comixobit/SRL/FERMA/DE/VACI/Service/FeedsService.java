package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.FeedsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedsService {

    private final FeedsRepository feedsRepository;


    public FeedsService(FeedsRepository feedsRepository) {
        this.feedsRepository = feedsRepository;
    }

    public List<FurajeModel> selectAllFeeds(){
        return feedsRepository.findAll();
    }

    public FurajeModel findById(Integer id){
        return feedsRepository.getOne(id);
    }

    public FurajeModel saveFeeds(FurajeModel furajeModel){
        int totalAmount = furajeModel.getCantitateaPrimita() * furajeModel.getCostulPerUnitate();
        furajeModel.setCostulTotal(totalAmount);
        return  feedsRepository.save(furajeModel);
    }

    public void deleteById(Integer id){
        feedsRepository.deleteById(id);
    }

    public FurajeModel findByName(Integer id){
        return feedsRepository.getOne(id);
    }


}

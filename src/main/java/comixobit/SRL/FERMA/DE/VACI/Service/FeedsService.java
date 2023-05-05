package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.FeedsRepository;
import org.springframework.data.repository.query.Param;
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

    public int selectQuantity(){
        return feedsRepository.sumQuantity();
    }

    public int selectTotalCost(){
        return feedsRepository.sumTotalCost();
    }

    public int selectTotalCostColumns(){
        return feedsRepository.sumTotalColumns();
    }

    public int selectTotalCostJanuary(){
        return feedsRepository.sumTotalJanuary();
    }

    public int selectTotalCostFeburary(){
        return feedsRepository.sumTotalFebruary();
    }

    public int selectTotalCostMarch(){
        return feedsRepository.sumTotalMarch();
    }

    public int selectTotalCostApril(){
        return feedsRepository.sumTotalApril();
    }

    public int selectTotalCostMay(){
        return feedsRepository.sumTotalMay();
    }

    public int selectTotalCostJune(){
        return feedsRepository.sumTotalJune();
    }

    public int selectTotalCostJuly(){
        return feedsRepository.sumTotalJuly();
    }

    public int selectTotalCostAugust(){
        return feedsRepository.sumTotalAugust();
    }

    public int selectTotalCostSeptember(){
        return feedsRepository.sumTotalSeptember();
    }

    public int selectTotalCostOctober(){
        return feedsRepository.sumTotalOctober();
    }

    public int selectTotalCostNovember(){
        return feedsRepository.sumTotalNovember();
    }

    public int selectTotalCostDecember(){
        return feedsRepository.sumTotalDecember();
    }



}

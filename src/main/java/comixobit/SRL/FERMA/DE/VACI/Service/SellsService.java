package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.ClientsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.SellsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SellsService {
    @Autowired
    private SellsRepository sellsRepository;

    @Autowired
    private LivestockProduceRepository livestockProduceRepository;

    @Autowired
    private LivestockService livestockService;
    @Autowired
    private ClientsRepository clientsRepository;

    public List<VanzariModel> selectAllSoldLivestock() {
        return sellsRepository.getClientiVanzariProduse();
    }

    public VanzariModel findById(Integer id) {
        return sellsRepository.getOne(id);
    }

    public void saveSoldLivestock(VanzariModel vanzariModel,
                                  int cantitatea,
                                  String organizatia,
                                  String tipProdus,
                                  Date dataExpirarii) {
        ClientiModel clientiModel = clientsRepository.findByOrganizatia(organizatia);
        ProduseZootehniceModel produseZootehniceModel = livestockProduceRepository.findByTipProdusAndDataExpirarii(tipProdus, dataExpirarii);

        if (clientiModel == null) {
            throw new RuntimeException("ClientiModel not found");
        }

        if (produseZootehniceModel == null) {
            throw new RuntimeException("ProduseZootehniceModel not found");
        }

        if (cantitatea > produseZootehniceModel.getCantitate()) {
            throw new RuntimeException("Nu este destula cantitate");
        }

        vanzariModel.setClientiModel(clientiModel);
        vanzariModel.setProduseZootehniceModel(produseZootehniceModel);


        // сохраняем объект VanzariModel
        sellsRepository.save(vanzariModel);

        // уменьшаем количество продукта в ProduseZootehniceModel
        int newQuantity = produseZootehniceModel.getCantitate() - cantitatea;
        produseZootehniceModel.setCantitate(newQuantity);
        livestockProduceRepository.save(produseZootehniceModel);
    }


    public void deleteById(Integer id){
        sellsRepository.deleteById(id);
    }

    public List<String> findByTipProdus(String tipProdus){
        return sellsRepository.searchDistinctByTipProdus(tipProdus);
    }
    public List<String> findByOrganizatia(String organizatia){
        return sellsRepository.searchDistinctByOrganizatia(organizatia);
    }

    public List<String > findByDataExpirarii(String dataExpirarii){
        return sellsRepository.searchDistinctByDataExpirarii(dataExpirarii);
    }

    public int selectTotalCostJanuaryIncomes(){
        return clientsRepository.sumTotalJanuary();
    }

    public int selectTotalCostFebruaryIncomes(){
        return clientsRepository.sumTotalFebruary();
    }

    public int selectTotalCostMarchIncomes(){
        return clientsRepository.sumTotalMarch();
    }

    public int selectTotalCostAprilIncomes(){
        return clientsRepository.sumTotalApril();
    }

    public int selectTotalCostMayIncomes(){
        return clientsRepository.sumTotalMay();
    }

    public int selectTotalCostJuneIncomes(){
        return clientsRepository.sumTotalJune();
    }

    public int selectTotalCostJulyIncomes(){
        return clientsRepository.sumTotalJuly();
    }

    public int selectTotalCostAugustIncomes(){
        return clientsRepository.sumTotalAugust();
    }

    public int selectTotalCostSeptemberIncomes(){
        return clientsRepository.sumTotalSeptember();
    }

    public int selectTotalCostOctoberIncomes(){
        return clientsRepository.sumTotalOctober();
    }

    public int selectTotalCostNovemberIncomes(){
        return clientsRepository.sumTotalNovember();
    }

    public int selectTotalCostDecemberIncomes(){
        return clientsRepository.sumTotalDecember();
    }

}

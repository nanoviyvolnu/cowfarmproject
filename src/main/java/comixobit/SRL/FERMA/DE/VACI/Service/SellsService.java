package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class
SellsService {
    @Autowired
    private SellsRepository sellsRepository;

    @Autowired
    private LivestockProduceRepository livestockProduceRepository;

    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private ClientsRepository clientsRepository;

    public List<VanzariModel> selectAllSoldLivestock() {
        return sellsRepository.getClientiVanzariProduse();
    }

    public Page<VanzariModel> selectAllSoldLivestockPage(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return sellsRepository.getClientiVanzariProdusePage(pageable);
    }

    public VanzariModel findById(Integer id) {
        return sellsRepository.getOne(id);
    }

    public void saveSoldLivestock(VanzariModel vanzariModel,
                                  Integer idLot,
                                  int cantitate,
                                  String organizatia) {
        ProduseZootehniceModel produseZootehniceModel = livestockProduceRepository.getOne(idLot);
        VacaModel vacaModel = new VacaModel();
        ClientiModel clientiModel = clientsRepository.findByOrganizatia(organizatia);

        if (cantitate > produseZootehniceModel.getCantitate()) {
            throw new RuntimeException("Nu este destula cantitate!");
        }

        if(vacaModel == null){
            vanzariModel.setVacaModel(null);
        }

        vanzariModel.setClientiModel(clientiModel);
        vanzariModel.setProduseZootehniceModel(produseZootehniceModel);


        sellsRepository.save(vanzariModel);

        int newQuantity = produseZootehniceModel.getCantitate() - cantitate;
        produseZootehniceModel.setCantitate(newQuantity);
        livestockProduceRepository.save(produseZootehniceModel);
    }

    public void saveSoldCows(VanzariModel vanzariModel,
                             Integer idVaca,
                             Integer cantitatea,
                             String organizatia) {
        VacaModel vacaModel = cowRepository.getOne(idVaca);
        ProduseZootehniceModel produseZootehniceModel = new ProduseZootehniceModel();
        ClientiModel clientiModel = clientsRepository.findByOrganizatia(organizatia);

        if (clientiModel == null) {
            throw new RuntimeException("Nu este inregistrat asa client!");
        }

        if(String.valueOf(cantitatea) == null){
            vanzariModel.setCantitate(1);
        }

        if(produseZootehniceModel == null){
            vanzariModel.setProduseZootehniceModel(null);
        }

        vanzariModel.setClientiModel(clientiModel);
        vanzariModel.setVacaModel(vacaModel);

        String statutul = "Vandut";
        vacaModel.setStatutul(statutul);


        sellsRepository.save(vanzariModel);
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

package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.InventarModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LivestockService {
    private final LivestockProduceRepository livestockProduceRepository;


    public LivestockService(LivestockProduceRepository livestockProduceRepository) {
        this.livestockProduceRepository = livestockProduceRepository;
    }

    public List<ProduseZootehniceModel> selectAllProduceZoo(){
        return livestockProduceRepository.findAll();
    }

    public ProduseZootehniceModel findById(Integer id){
        return livestockProduceRepository.getOne(id);
    }

    public Page<ProduseZootehniceModel> findPage(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return livestockProduceRepository.findAll(pageable);
    }

    public ProduseZootehniceModel saveProduceZoo(ProduseZootehniceModel produseZootehniceModel) {
        Optional<ProduseZootehniceModel> existingRecord = Optional.ofNullable((livestockProduceRepository.findByTipProdusAndDataExpirarii(
                produseZootehniceModel.getTipProdus(), produseZootehniceModel.getDataExpirarii())));

        if (existingRecord.isPresent()) {
            ProduseZootehniceModel currentRecord = existingRecord.get();
            currentRecord.setCantitate(currentRecord.getCantitate() + produseZootehniceModel.getCantitate());
            return livestockProduceRepository.save(currentRecord);
        } else {
            return livestockProduceRepository.save(produseZootehniceModel);
        }
    }


    public void deleteById(Integer id){
        livestockProduceRepository.deleteById(id);
    }

    public ProduseZootehniceModel findByName(Integer id){
        return livestockProduceRepository.getOne(id);
    }


}

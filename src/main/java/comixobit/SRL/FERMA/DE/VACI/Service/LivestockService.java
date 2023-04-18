package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ProduseZootehniceModel saveProduceZoo(ProduseZootehniceModel produseZootehniceModel){
        return  livestockProduceRepository.save(produseZootehniceModel);
    }

    public void deleteById(Integer id){
        livestockProduceRepository.deleteById(id);
    }

    public ProduseZootehniceModel findByName(Integer id){
        return livestockProduceRepository.getOne(id);
    }
}

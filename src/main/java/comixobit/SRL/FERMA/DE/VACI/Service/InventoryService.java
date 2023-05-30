package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.InventarModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventarModel> selectAllTools(){
        return inventoryRepository.findAll();
    }

    public Page<InventarModel> findPage(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return inventoryRepository.findAll(pageable);
    }

    public InventarModel findById(Integer id){
        return inventoryRepository.getOne(id);
    }

    public InventarModel saveTools(InventarModel inventarModel){
        int totalAmount = inventarModel.getPretul() * inventarModel.getCantitate();
        inventarModel.setCostulTotal(totalAmount);
        return  inventoryRepository.save(inventarModel);
    }

    public void updateById(InventarModel inventarModel){
        inventoryRepository.save(inventarModel);
    }

    public void deleteById(Integer id){
        inventoryRepository.deleteById(id);
    }


}

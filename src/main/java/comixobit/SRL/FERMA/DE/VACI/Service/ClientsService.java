package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.ClientsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository clientsRepository;


    public List<ClientiModel> selectAllClient(){
        return clientsRepository.findAll();
    }

    public ClientiModel findById(Integer id){
        return clientsRepository.getOne(id);
    }

    public ClientiModel saveClient(ClientiModel clientiModel){
        return  clientsRepository.save(clientiModel);
    }

    public void updateById(ClientiModel clientiModel, int idClient){
        clientsRepository.save(clientiModel);
    }

    public void deleteById(Integer id){
        clientsRepository.deleteById(id);
    }
}

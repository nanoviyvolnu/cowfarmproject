package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.GroupsModel;
import comixobit.SRL.FERMA.DE.VACI.Models.GrupuriVaciModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.GroupsCowsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.GroupsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsCowService {

    private final GroupsCowsRepository groupsCowsRepository;
    private final GroupsRepository groupsRepository;

    public GroupsCowService(GroupsCowsRepository groupsCowsRepository, GroupsRepository groupsRepository) {
        this.groupsCowsRepository = groupsCowsRepository;
        this.groupsRepository = groupsRepository;
    }

    public List<GrupuriVaciModel> findByCowGroup(GroupsModel groupsModel){
        return groupsCowsRepository.findByGroupsModel(groupsModel);
    }

    public int totalCountCowsGroup(Integer idGrup){
        return groupsCowsRepository.totalCowsInGroup(idGrup);
    }
}

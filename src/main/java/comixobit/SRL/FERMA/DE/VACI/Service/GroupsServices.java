package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.GroupsModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.GroupsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsServices {
    private final GroupsRepository groupsRepository;

    public GroupsServices(GroupsRepository groupsRepository1) {
        this.groupsRepository = groupsRepository1;
    }

    public List<GroupsModel> selectAllGroups(){
        return groupsRepository.findAll();
    }

    public GroupsModel findById(Integer id){
        return groupsRepository.getOne(id);
    }

    public GroupsModel saveGroup(GroupsModel groupsModel){
        return  groupsRepository.save(groupsModel);
    }

    public void deleteById(Integer id){
        groupsRepository.deleteById(id);
    }

    public GroupsModel findByName(Integer id){
        return groupsRepository.getOne(id);
    }
}

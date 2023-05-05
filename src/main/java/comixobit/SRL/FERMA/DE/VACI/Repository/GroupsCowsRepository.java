package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.GroupsModel;
import comixobit.SRL.FERMA.DE.VACI.Models.GrupuriVaciModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupsCowsRepository extends JpaRepository<GrupuriVaciModel, Integer> {
    List<GrupuriVaciModel> findByGroupsModel(GroupsModel groupsModel);

    @Query(value = "SELECT COUNT(*) FROM grupuri_vaci_model WHERE id_grup = :idGrup", nativeQuery = true)
    int totalCowsInGroup(Integer idGrup);

    void deleteByGroupsModel(GroupsModel groupsModel);
}

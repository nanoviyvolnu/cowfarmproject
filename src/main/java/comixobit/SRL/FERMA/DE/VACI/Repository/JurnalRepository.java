package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.JurnalModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaFinalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JurnalRepository extends JpaRepository<JurnalModel, Integer> {
    @Query(value = "SELECT jurnal.Id_event, vaca.Codul, vaca.Photo, vaca.Rasa, vaca.Data_nasterii, " +
            "vaca.Data_insarcinarii," +
            "jurnal.Status, vaca.Id_vaca AS id_vaca, jurnal.Id_event AS id FROM jurnal " +
            "INNER JOIN vaca ON vaca.Id_vaca = jurnal.Id_vaca = vaca.Id_vaca", nativeQuery = true)
    Page<JurnalModel> getAllCows(Pageable pageable);
}

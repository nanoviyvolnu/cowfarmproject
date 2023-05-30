package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaFinalModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CowFinalRepository extends JpaRepository<VacaFinalModel, Integer> {


    @Query(value = "SELECT vaca.Codul, vaca.Photo, vaca.Rasa, vaca.Masa_kg_initiala, " +
            "vaca.Masa_kg_moment, vaca.Statutul, vaca.Genul," +
            "vaca.Categorie, vacafinal.data, vaca.Id_vaca AS id_vaca, vacafinal.id AS id FROM vacafinal " +
            "INNER JOIN vaca ON vaca.Id_vaca = vacafinal.Id_vaca", nativeQuery = true)
    List<VacaFinalModel> getAllDiedCows();

    @Query(value = "SELECT vaca.Photo, vaca.Rasa, vaca.Masa_kg_initiala, " +
            "vaca.Masa_kg_moment, vaca.Statutul, vaca.Genul," +
            "vaca.Categorie, vacafinal.data, vaca.Id_vaca AS id_vaca, vacafinal.id AS id FROM vacafinal " +
            "INNER JOIN vaca ON vaca.Id_vaca = vacafinal.Id_vaca WHERE Statutul = 'Taiat' OR Statutul = 'Mort'", nativeQuery = true)
    List<VacaFinalModel> getAllDiedCowsToPDF();

    @Query(value = "SELECT vaca.Photo, vaca.Rasa, vaca.Masa_kg_initiala, " +
            "vaca.Masa_kg_moment, vaca.Statutul, vaca.Genul," +
            "vaca.Categorie, vacafinal.data, vaca.Id_vaca AS id_vaca, vacafinal.id AS id FROM vacafinal " +
            "INNER JOIN vaca ON vaca.Id_vaca = vacafinal.Id_vaca WHERE Statutul = 'Vandut'", nativeQuery = true)
    List<VacaFinalModel> getAllSoldedCowsToPDF();

    @Query(value = "SELECT vaca.Photo, vaca.Rasa, vaca.Masa_kg_initiala, " +
            "vaca.Masa_kg_moment, vaca.Statutul, vaca.Genul," +
            "vaca.Categorie, vacafinal.data, vaca.Id_vaca AS id_vaca, vacafinal.id AS id FROM vacafinal " +
            "INNER JOIN vaca ON vaca.Id_vaca = vacafinal.Id_vaca", nativeQuery = true)
    Page<VacaFinalModel> getPageAllDiedCows(Pageable pageable);


    @Query(value = "SELECT vaca.Photo, vaca.Rasa, vaca.Masa_kg_initiala, " +
            "vaca.Masa_kg_moment, vaca.Statutul, vaca.Genul,vaca.Categorie, " +
            "vacafinal.data, vaca.Id_vaca AS id_vaca, " +
            "vacafinal.id AS id " +
            "FROM vacafinal " +
            "INNER JOIN vaca ON vaca.Id_vaca = vacafinal.Id_vaca WHERE vaca.rasa = ?1",
            nativeQuery = true)
    Page<VacaFinalModel> findByRasaDiedCow(String rasa, Pageable pageable);
}

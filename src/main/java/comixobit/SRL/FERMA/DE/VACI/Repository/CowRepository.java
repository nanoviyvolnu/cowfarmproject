package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface CowRepository extends JpaRepository<VacaModel, Integer>, JpaSpecificationExecutor<VacaModel> {

    List<VacaModel> findByDataInsarcinareBefore(Date currentDate);

    @Query(value = "SELECT * FROM vaca WHERE Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> listActiveCows(Pageable pageable);

    @Query(value = "SELECT * FROM vaca WHERE Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    List<VacaModel> listActiveCowsToPDF();

    @Query(value = "SELECT * FROM vaca WHERE Statutul = 'Insarcinata' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> listPregnatCows(Pageable pageable);

    @Query(value = "SELECT * FROM vaca WHERE Statutul = 'Insarcinata' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    List<VacaModel> listPregnatCowsToPDF();

    @Query(value = "SELECT * FROM vaca WHERE Starea_sanatatii = 'Bolnav' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> listSickCows(Pageable pageable);

    @Query(value = "SELECT * FROM vaca WHERE Starea_sanatatii = 'Bolnav' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    List<VacaModel> listSickCowsToPDF();

    @Query(value = "SELECT * FROM vaca WHERE Rasa = ?1 AND Statutul = 'Insarcinata' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> findByRasa(String rasa, Pageable pageable);

    @Query(value = "SELECT * FROM vaca WHERE Rasa = ?1 AND Statutul = 'Insarcinata' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> findByRasaPregnatCows(String rasa, Pageable pageable);

    @Query(value = "SELECT * FROM vaca WHERE Rasa = ?1 AND Starea_sanatatii = 'Bolnav' AND Statutul != 'Mort' AND Statutul != 'Vandut' AND Statutul != 'Mort'", nativeQuery = true)
    Page<VacaModel> findByRasaSickCows(String rasa, Pageable pageable);

    @Query(value = "SELECT DISTINCT e.Rasa FROM vaca e ORDER BY e.Rasa", nativeQuery = true)
    @Transactional(readOnly = true)
    List<String> searchDistinctByRasa(String rasa);

    @Query(value = "SELECT DISTINCT e.Categorie FROM vaca e ORDER BY e.Categorie", nativeQuery = true)
    @Transactional(readOnly = true)
    List<String> searchDistinctByCategorie(String categorie);

    @Query(value = "SELECT DISTINCT e.Statutul FROM vaca e ORDER BY e.Statutul", nativeQuery = true)
    @Transactional(readOnly = true)
    List<String> searchDistinctByStatutul(String statutul);

    @Query(value = "SELECT DISTINCT e.Forma_achizitie FROM vaca e ORDER BY e.Forma_achizitie", nativeQuery = true)
    @Transactional(readOnly = true)
    List<String> searchDistinctByFormaAchizitie(String formaAchizitie);

    @Query(value = "SELECT Categorie, COUNT(*) FROM  vaca GROUP BY Categorie", nativeQuery = true)
    List<Object> getCountByCategorie();

    // masa kg intiala
    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_initiala > ?1", nativeQuery = true)
    List<String> searchAllByMasaKgInitialaLessThan(int lessMasaInitiala);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_initiala >= ?1", nativeQuery = true)
    List<String> searchAllByMasaKgInitialaLessThanEqual(int lessEqualMasaInitiala);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_initiala = ?1", nativeQuery = true)
    List<String> searchAllByMasaKgInitialaEquals(int equalsMasaInitiala);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_initiala <?1", nativeQuery = true)
    List<String> searchAllByMasaKgInitialaGreaterThan(int graterMasaInitiala);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_initiala <= ?1", nativeQuery = true)
    List<String> searchAllByMasaKgInitialaGreaterThanEqual(int graterEqualMasaInitiala);

    //masa kg moment
    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_moment > ?1", nativeQuery = true)
    List<String> searchAllByMasaKgMomentLessThan(int lessMasaMoment);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_moment >= ?1", nativeQuery = true)
    List<String> searchAllByMasaKgMomentLessThanEqual(int lessEqualMasaMoment);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_moment = ?1", nativeQuery = true)
    List<String> searchAllByMasaKgMomentEquals(int equalsMasaInitiala);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_moment <?1", nativeQuery = true)
    List<String> searchAllByMasaKgMomentGreaterThan(int greaterMasaMoment);

    @Query(value = "SELECT * FROM vaca v WHERE v.Masa_kg_moment <= ?1", nativeQuery = true)
    List<String> searchAllByMasaKgMomentGreaterThanEqual(int greaterEqualMasaMoment);
}

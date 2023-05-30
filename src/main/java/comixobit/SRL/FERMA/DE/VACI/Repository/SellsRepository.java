package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface SellsRepository extends JpaRepository<VanzariModel, Integer> {
    @Query(value = "SELECT DISTINCT e.tip_produs FROM produsezootehnice e ORDER BY e.tip_produs", nativeQuery = true)
    @Transactional(readOnly = true )
    List<String> searchDistinctByTipProdus(String tipProdus);

    @Query(value = "SELECT DISTINCT e.organizatia FROM clienti e ORDER BY e.Organizatia", nativeQuery = true)
    @Transactional(readOnly = true )
    List<String> searchDistinctByOrganizatia(String organizatia);

    @Query(value = "SELECT DISTINCT e.data_expirarii FROM produsezootehnice e ORDER BY e.data_expirarii", nativeQuery = true)
    @Transactional(readOnly = true )
    List<String> searchDistinctByDataExpirarii(String dataExpirarii);

    List<VanzariModel> findByProduseZootehniceModel(ProduseZootehniceModel produseZootehniceModel);

    @Query(value = "SELECT vanzari.Id_vanzare, clienti.Nume, clienti.Prenume,\n" +
            "clienti.Email, clienti.Nr_tel, clienti.Organizatia, vanzari.Cantitate, produsezootehnice.tip_produs,\n" +
            "vaca.Rasa, vaca.Categorie,\n" +
            "produsezootehnice.data_expirarii, vanzari.Data_vanzare, vanzari.Pretul,\n" +
            "clienti.Id_client AS id_client,\n" +
            "produsezootehnice.Id_lot AS id_lot,\n" +
            "vaca.Id_vaca AS id_vaca\n" +
            "FROM vanzari\n" +
            "LEFT JOIN produsezootehnice ON  produsezootehnice.id_lot = vanzari.Id_lot\n" +
            "LEFT JOIN clienti ON clienti.Id_client = vanzari.Id_client \n" +
            "LEFT JOIN vaca ON vaca.Id_vaca = vanzari.Id_vaca " +
            "ORDER BY vanzari.Data_vanzare DESC", nativeQuery = true)
    Page<VanzariModel> getClientiVanzariProdusePage(Pageable pageable);


    @Query(value = "SELECT vanzari.Id_vanzare, clienti.Nume, clienti.Prenume,\n" +
            "clienti.Email, clienti.Nr_tel, clienti.Organizatia, vanzari.Cantitate, produsezootehnice.tip_produs,\n" +
            "vaca.Rasa, vaca.Categorie,\n" +
            "produsezootehnice.data_expirarii, vanzari.Data_vanzare, vanzari.Pretul,\n" +
            "clienti.Id_client AS id_client,\n" +
            "produsezootehnice.Id_lot AS id_lot,\n" +
            "vaca.Id_vaca AS id_vaca\n" +
            "FROM vanzari\n" +
            "LEFT JOIN produsezootehnice ON  produsezootehnice.id_lot = vanzari.Id_lot\n" +
            "LEFT JOIN clienti ON clienti.Id_client = vanzari.Id_client \n" +
            "LEFT JOIN vaca ON vaca.Id_vaca = vanzari.Id_vaca " +
            "ORDER BY vanzari.Data_vanzare DESC", nativeQuery = true)
    List<VanzariModel> getClientiVanzariProduse();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJanuary(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenFebruary(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenMarch(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenApril(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenMay(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJune(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJuly(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenAugust(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenSeptember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenOctober(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenNovember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari WHERE MONTH(Data_vanzare) = :month AND YEAR(Data_vanzare) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenDecember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface FeedsRepository extends JpaRepository<FurajeModel, Integer> {
    @Query(value = "SELECT COALESCE(SUM(cantitatea_primita), 0) FROM furaje", nativeQuery = true)
    int sumQuantity();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje", nativeQuery = true)
    int sumTotalColumns();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = MONTH(current_date())", nativeQuery = true)
    int sumTotalCost();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 1 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJanuary();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 2 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalFebruary();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 3 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalMarch();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 4 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalApril();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 5 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalMay();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 6 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJune();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 7 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJuly();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 8 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalAugust();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 9 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalSeptember();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 10 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalOctober();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 11 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalNovember();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje where MONTH(data_procurarii) = 12 AND YEAR(data_procurarii) = YEAR(current_date())", nativeQuery = true)
    int sumTotalDecember();

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJanuary(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenFebruary(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenMarch(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenApril(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenMay(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJune(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenJuly(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenAugust(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenSeptember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenOctober(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenNovember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COALESCE(SUM(costul_total), 0) FROM furaje WHERE MONTH(data_procurarii) = :month AND YEAR(data_procurarii) BETWEEN YEAR(:startDate) AND YEAR(:endDate)", nativeQuery = true)
    int findByDataProcurariiBetweenDecember(@Param("month") Integer month, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

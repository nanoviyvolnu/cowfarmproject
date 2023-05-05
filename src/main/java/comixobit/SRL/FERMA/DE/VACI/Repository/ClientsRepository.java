package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<ClientiModel, Integer> {
//    @Query(value = "SELECT e.* FROM clienti e where e.Organizatia = ?1", nativeQuery = true)
//    @Transactional(readOnly = true )
//    ClientiModel findByOrganizatia(String organizatia);

    ClientiModel findByOrganizatia(String organizatia);


    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 1 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJanuary();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 2 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalFebruary();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 3 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalMarch();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 4 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalApril();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 5 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalMay();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 6 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJune();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 7 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalJuly();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 8 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalAugust();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 9 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalSeptember();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 10 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalOctober();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 11 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalNovember();

    @Query(value = "SELECT COALESCE(SUM(Pretul), 0) FROM vanzari where MONTH(Data_vanzare) = 12 AND YEAR(Data_vanzare) = YEAR(current_date())", nativeQuery = true)
    int sumTotalDecember();
}

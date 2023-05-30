package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@EnableJpaRepositories

public interface LivestockProduceRepository extends JpaRepository<ProduseZootehniceModel, Integer> {

    @Query(value ="UPDATE produsezootehnice SET cantitate = ?1 WHERE tip_produs = ?2 AND data_expirarii = ?3 ", nativeQuery = true)
    int updateCantitateIfExists(@Param("cantitate") Integer cantitate,
                                @Param("tipProdus") String tipProdus,
                                @Param("dataExpirarii") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataExpirarii);


    boolean existsProduseZootehniceModelByTipProdusAndDataExpirarii(@Param("tipProdus") String tipProdus,
                                                                    @Param("dataExpirarii") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataExpirarii);

    ProduseZootehniceModel findByTipProdusAndDataExpirarii(String tipProduus, Date dataExpirarii);
}

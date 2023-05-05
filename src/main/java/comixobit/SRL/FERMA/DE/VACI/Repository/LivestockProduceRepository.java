package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories

public interface LivestockProduceRepository extends JpaRepository<ProduseZootehniceModel, Integer> {
//    @Query(value = "SELECT e.* FROM produsezootehnice e WHERE e.data_expirarii = ?1 AND e.tip_produs = ?2", nativeQuery = true)
//    @Transactional(readOnly = true )
//    ProduseZootehniceModel findByTipProdusAndDataExpirarii(String tipProdus, Date dataExpirarii);


    ProduseZootehniceModel findByTipProdusAndDataExpirarii(String tipProduus, Date dataExpirarii);
}

package comixobit.SRL.FERMA.DE.VACI.Repository;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<LucratorModel, Integer> {
    boolean existsByIdnp(String idnp);

    @Query(value = "SELECT remunerare_pe_ora, COUNT(*) FROM lucrator GROUP BY remunerare_pe_ora", nativeQuery = true)
    List<Object> getCountByRemunerarePeOra();
}

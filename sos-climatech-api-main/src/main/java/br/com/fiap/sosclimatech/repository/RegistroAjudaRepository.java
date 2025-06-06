package br.com.fiap.sosclimatech.repository;
import br.com.fiap.sosclimatech.model.RegistroAjuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
public interface RegistroAjudaRepository extends JpaRepository<RegistroAjuda, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE RegistroAjuda r SET r.entregue = true WHERE r.id = :id")
    void marcarComoEntregue(Long id);
}

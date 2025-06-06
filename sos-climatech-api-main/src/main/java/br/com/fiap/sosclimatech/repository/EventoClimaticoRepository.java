package br.com.fiap.sosclimatech.repository;
import br.com.fiap.sosclimatech.model.EventoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventoClimaticoRepository extends JpaRepository<EventoClimatico, Long> {
}
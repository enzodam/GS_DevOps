package br.com.fiap.sosclimatech.repository;
import br.com.fiap.sosclimatech.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
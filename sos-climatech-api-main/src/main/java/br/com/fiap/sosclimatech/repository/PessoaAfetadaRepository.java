package br.com.fiap.sosclimatech.repository;
import br.com.fiap.sosclimatech.model.PessoaAfetada;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PessoaAfetadaRepository extends JpaRepository<PessoaAfetada, Long> {
}
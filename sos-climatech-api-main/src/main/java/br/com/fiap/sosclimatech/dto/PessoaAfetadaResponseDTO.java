package br.com.fiap.sosclimatech.dto;
import br.com.fiap.sosclimatech.model.PessoaAfetada;
import br.com.fiap.sosclimatech.model.StatusAssistencia;
public record PessoaAfetadaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String endereco,
        StatusAssistencia statusAssistencia,
        Long eventoId, 
        String eventoTipo 
) {
    public PessoaAfetadaResponseDTO(PessoaAfetada pessoa) {
        this(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getCpf(),
                pessoa.getEndereco(),
                pessoa.getStatusAssistencia(),
                pessoa.getEvento() != null ? pessoa.getEvento().getId() : null,
                pessoa.getEvento() != null ? pessoa.getEvento().getTipo() : null
        );
    }
}

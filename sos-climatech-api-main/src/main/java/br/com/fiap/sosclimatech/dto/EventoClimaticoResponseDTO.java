package br.com.fiap.sosclimatech.dto;
import java.time.LocalDate;
public record EventoClimaticoResponseDTO(
        Long id,
        String tipo,
        LocalDate dataInicio,
        LocalidadeResponseDTO localidade,
        String impacto
) {
    public record LocalidadeResponseDTO(
            Long id,
            String cidade,
            String estado
    ) {}
}

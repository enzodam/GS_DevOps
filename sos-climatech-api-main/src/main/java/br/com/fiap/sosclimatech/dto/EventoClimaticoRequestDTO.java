package br.com.fiap.sosclimatech.dto;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
public record EventoClimaticoRequestDTO(
        @NotBlank(message = "Tipo do evento não pode ser vazio")
        @Size(max = 50, message = "Tipo do evento deve ter no máximo 50 caracteres")
        String tipo,
        @NotNull(message = "Data de início não pode ser nula")
        @FutureOrPresent(message = "Data de início deve ser no presente ou futuro")
        LocalDate dataInicio,
        @NotNull(message = "ID da Localidade não pode ser nulo")
        Long localidadeId,
        @Size(max = 300, message = "Descrição do impacto deve ter no máximo 300 caracteres")
        String impacto
) {}

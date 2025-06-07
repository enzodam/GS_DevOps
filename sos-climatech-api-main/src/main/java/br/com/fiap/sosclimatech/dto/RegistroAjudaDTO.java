package br.com.fiap.sosclimatech.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAjudaDTO {

    private Long id;

    @NotNull(message = "ID da pessoa é obrigatório")
    private Long pessoaId;

    @NotNull(message = "ID do recurso é obrigatório")
    private Long recursoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    private Integer quantidade;

    private LocalDateTime dataRegistro;

    private boolean entregue;

}

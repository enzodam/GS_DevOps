package br.com.fiap.sosclimatech.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecursoDTO {
    private Long id;
    @NotBlank(message = "Nome do recurso é obrigatório")
    private String nome;
    @NotBlank(message = "Tipo do recurso é obrigatório")
    private String tipo;
    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "Quantidade deve ser positiva ou zero")
    private Integer quantidade;
    private Long eventoId;
}
package br.com.fiap.sosclimatech.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaAfetadaDTO {
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
    private boolean prioridade;
    private Long eventoId;
}
package br.com.fiap.sosclimatech.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadeDTO {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String cidade;
    @NotBlank
    @Size(min = 2, max = 2)
    private String estado;
}

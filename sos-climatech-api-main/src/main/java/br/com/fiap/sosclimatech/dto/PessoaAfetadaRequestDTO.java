package br.com.fiap.sosclimatech.dto;
import br.com.fiap.sosclimatech.model.StatusAssistencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public record PessoaAfetadaRequestDTO(
        @NotBlank(message = "Nome da pessoa não pode ser vazio")
        @Size(max = 100, message = "Nome da pessoa deve ter no máximo 100 caracteres")
        String nome,
        @NotBlank(message = "CPF não pode ser vazio")
        @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)|(^\\d{11}$)", message = "CPF inválido. Formato esperado: XXX.XXX.XXX-XX ou XXXXXXXXXXX")
        String cpf,
        @NotBlank(message = "Endereço não pode ser vazio")
        @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
        String endereco,
        @NotNull(message = "ID do evento climático relacionado não pode ser nulo")
        Long eventoId 
) {}

package br.com.fiap.sosclimatech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") 
@Entity
@Table(name = "TB_PESSOA_AFETADA")
public class PessoaAfetada {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PESSOA")
    @SequenceGenerator(name = "SQ_PESSOA", sequenceName = "SQ_PESSOA_AFETADA", allocationSize = 1) 
    @Column(name = "ID_PESSOA")
    private Long id;
    @NotBlank(message = "Nome da pessoa não pode ser vazio")
    @Size(max = 100, message = "Nome da pessoa deve ter no máximo 100 caracteres")
    @Column(name = "NM_PESSOA", nullable = false, length = 100)
    private String nome;
    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)|(^\\d{11}$)", message = "CPF inválido")
    @Column(name = "NR_CPF", length = 14, unique = true, nullable = false) 
    private String cpf;
    @NotBlank(message = "Endereço não pode ser vazio")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Column(name = "DS_ENDERECO", nullable = false, length = 200) 
    private String endereco;
    @NotNull(message = "Status de assistência não pode ser nulo")
    @Enumerated(EnumType.STRING) 
    @Column(name = "ST_ASSISTENCIA", nullable = false)
    private StatusAssistencia statusAssistencia = StatusAssistencia.REGISTRADO; 
    @NotNull(message = "Evento climático relacionado não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_EVENTO", nullable = false) 
    private EventoClimatico evento;
}

package br.com.fiap.sosclimatech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TB_EVENTO_CLIMATICO")
public class EventoClimatico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVENTO")
    @SequenceGenerator(name = "SQ_EVENTO", sequenceName = "SQ_EVENTO_CLIMATICO", allocationSize = 1)
    @Column(name = "ID_EVENTO")
    private Long id;
    @NotBlank(message = "Tipo do evento não pode ser vazio")
    @Size(max = 50, message = "Tipo do evento deve ter no máximo 50 caracteres")
    @Column(name = "TP_EVENTO", nullable = false, length = 50)
    private String tipo;
    @NotNull(message = "Data de início não pode ser nula")
    @FutureOrPresent(message = "Data de início deve ser no presente ou futuro")
    @Column(name = "DT_INICIO", nullable = false)
    private LocalDate dataInicio;
    @Size(max = 300, message = "Descrição do impacto deve ter no máximo 300 caracteres")
    @Column(name = "DS_IMPACTO", length = 300)
    private String impacto;
    @NotNull(message = "Localidade não pode ser nula")
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_LOCALIDADE", nullable = false) 
    private Localidade localidade; 
    @OneToMany(mappedBy = "evento", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<PessoaAfetada> pessoasAfetadas = new ArrayList<>();
    public void addPessoaAfetada(PessoaAfetada pessoa) {
        pessoasAfetadas.add(pessoa);
        pessoa.setEvento(this);
    }
    public void removePessoaAfetada(PessoaAfetada pessoa) {
        pessoasAfetadas.remove(pessoa);
        pessoa.setEvento(null);
    }
}

package br.com.fiap.sosclimatech.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_LOCALIDADE")
public class Localidade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOCALIDADE")
    @SequenceGenerator(name = "SQ_LOCALIDADE", sequenceName = "SQ_LOCALIDADE", allocationSize = 1)
    @Column(name = "ID_LOCALIDADE")
    private Long id;
    @Column(name = "NM_CIDADE", nullable = false)
    private String cidade;
    @Column(name = "SG_ESTADO", length = 2)
    private String estado;
    @OneToMany(mappedBy = "localidade")
    private List<EventoClimatico> eventos;
}

package br.com.fiap.sosclimatech.model;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_RECURSO")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RECURSO")
    @SequenceGenerator(name = "SQ_RECURSO", sequenceName = "SQ_RECURSO", allocationSize = 1)
    @Column(name = "ID_RECURSO")
    private Long id;
    @Column(name = "NM_RECURSO", nullable = false)
    private String nome;
    @Column(name = "TP_RECURSO")
    private String tipo;
    @Column(name = "QT_DISPONIVEL")
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "ID_EVENTO")
    private EventoClimatico evento;
}
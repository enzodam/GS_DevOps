package br.com.fiap.sosclimatech.service;
import br.com.fiap.sosclimatech.dto.RecursoDTO;
import br.com.fiap.sosclimatech.exception.ResourceNotFoundException;
import br.com.fiap.sosclimatech.model.EventoClimatico;
import br.com.fiap.sosclimatech.model.Recurso;
import br.com.fiap.sosclimatech.repository.EventoClimaticoRepository;
import br.com.fiap.sosclimatech.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecursoService {
    private final RecursoRepository recursoRepository;
    private final EventoClimaticoRepository eventoClimaticoRepository;
    @Autowired
    public RecursoService(RecursoRepository recursoRepository, EventoClimaticoRepository eventoClimaticoRepository) {
        this.recursoRepository = recursoRepository;
        this.eventoClimaticoRepository = eventoClimaticoRepository;
    }
    @Transactional(readOnly = true)
    public List<RecursoDTO> findAll() {
        return recursoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public RecursoDTO findById(Long id) {
        Recurso entity = recursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com ID: " + id));
        return toDTO(entity);
    }
    @Transactional
    public RecursoDTO save(RecursoDTO dto) {
        Recurso entity = toEntity(dto);
        entity = recursoRepository.save(entity);
        return toDTO(entity);
    }
    @Transactional
    public RecursoDTO update(Long id, RecursoDTO dto) {
        Recurso entity = recursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com ID: " + id));
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setQuantidade(dto.getQuantidade());
        if (dto.getEventoId() != null) {
            EventoClimatico evento = eventoClimaticoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento climático não encontrado com ID: " + dto.getEventoId()));
            entity.setEvento(evento);
        } else {
            entity.setEvento(null);
        }
        entity = recursoRepository.save(entity);
        return toDTO(entity);
    }
    @Transactional
    public void delete(Long id) {
        if (!recursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado com ID: " + id);
        }
        recursoRepository.deleteById(id);
    }
    private RecursoDTO toDTO(Recurso entity) {
        return new RecursoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                entity.getQuantidade(),
                entity.getEvento() != null ? entity.getEvento().getId() : null
        );
    }
    private Recurso toEntity(RecursoDTO dto) {
        Recurso entity = new Recurso();
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setQuantidade(dto.getQuantidade());
        if (dto.getEventoId() != null) {
            EventoClimatico evento = eventoClimaticoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento climático não encontrado com ID: " + dto.getEventoId()));
            entity.setEvento(evento);
        }
        return entity;
    }
}

package br.com.fiap.sosclimatech.service;
import br.com.fiap.sosclimatech.dto.EventoClimaticoRequestDTO;
import br.com.fiap.sosclimatech.dto.EventoClimaticoResponseDTO;
import br.com.fiap.sosclimatech.exception.ResourceNotFoundException;
import br.com.fiap.sosclimatech.model.EventoClimatico;
import br.com.fiap.sosclimatech.model.Localidade;
import br.com.fiap.sosclimatech.repository.EventoClimaticoRepository;
import br.com.fiap.sosclimatech.repository.LocalidadeRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EventoClimaticoService {
    private final EventoClimaticoRepository eventoClimaticoRepository;
    private final LocalidadeRepository localidadeRepository; 
    @Autowired
    public EventoClimaticoService(EventoClimaticoRepository eventoClimaticoRepository, LocalidadeRepository localidadeRepository) {
        this.eventoClimaticoRepository = eventoClimaticoRepository;
        this.localidadeRepository = localidadeRepository; 
    }
    @Transactional(readOnly = true)
    public List<EventoClimaticoResponseDTO> findAll() {
        List<EventoClimatico> eventos = eventoClimaticoRepository.findAll();
        return eventos.stream()
                .map(this::toResponseDTO) 
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public EventoClimaticoResponseDTO findById(Long id) {
        EventoClimatico evento = findEntityById(id);
        return toResponseDTO(evento); 
    }
    @Transactional
    public EventoClimaticoResponseDTO save(EventoClimaticoRequestDTO eventoDTO) {
        EventoClimatico evento = new EventoClimatico();
        mapDtoToEntity(eventoDTO, evento); 
        evento = eventoClimaticoRepository.save(evento);
        return toResponseDTO(evento); 
    }
    @Transactional
    public EventoClimaticoResponseDTO update(Long id, EventoClimaticoRequestDTO eventoDTO) {
        EventoClimatico evento = findEntityById(id);
        mapDtoToEntity(eventoDTO, evento); 
        evento = eventoClimaticoRepository.save(evento);
        return toResponseDTO(evento); 
    }
    @Transactional
    public void delete(Long id) {
        EventoClimatico evento = findEntityById(id);
        eventoClimaticoRepository.delete(evento);
    }
    private void mapDtoToEntity(EventoClimaticoRequestDTO dto, EventoClimatico entity) {
        entity.setTipo(dto.tipo());
        entity.setDataInicio(dto.dataInicio());
        entity.setImpacto(dto.impacto());
        Localidade localidade = localidadeRepository.findById(dto.localidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Localidade não encontrada com ID: " + dto.localidadeId()));
        entity.setLocalidade(localidade); 
    }
    private EventoClimaticoResponseDTO toResponseDTO(EventoClimatico entity) {
        EventoClimaticoResponseDTO.LocalidadeResponseDTO localidadeDTO = null;
        if (entity.getLocalidade() != null) {
            localidadeDTO = new EventoClimaticoResponseDTO.LocalidadeResponseDTO(
                    entity.getLocalidade().getId(),
                    entity.getLocalidade().getCidade(),
                    entity.getLocalidade().getEstado()
            );
        }
        return new EventoClimaticoResponseDTO(
                entity.getId(),
                entity.getTipo(),
                entity.getDataInicio(),
                localidadeDTO, 
                entity.getImpacto()
        );
    }
    protected EventoClimatico findEntityById(Long id) {
        return eventoClimaticoRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Evento Climático não encontrado com ID: " + id));
    }
}

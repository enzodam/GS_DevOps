package br.com.fiap.sosclimatech.service;
import br.com.fiap.sosclimatech.dto.LocalidadeDTO;
import br.com.fiap.sosclimatech.exception.ResourceNotFoundException;
import br.com.fiap.sosclimatech.model.Localidade;
import br.com.fiap.sosclimatech.repository.LocalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class LocalidadeService {
    private final LocalidadeRepository repository;
    @Autowired
    public LocalidadeService(LocalidadeRepository repository) {
        this.repository = repository;
    }
    @Transactional(readOnly = true)
    public List<LocalidadeDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public LocalidadeDTO findById(Long id) {
        Localidade entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidade não encontrada com ID: " + id));
        return toDTO(entity);
    }
    @Transactional
    public LocalidadeDTO save(LocalidadeDTO dto) {
        Localidade entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }
    @Transactional
    public LocalidadeDTO update(Long id, LocalidadeDTO dto) {
        Localidade entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localidade não encontrada com ID: " + id));
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity = repository.save(entity);
        return toDTO(entity);
    }
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Localidade não encontrada com ID: " + id);
        }
        repository.deleteById(id);
    }
    private LocalidadeDTO toDTO(Localidade entity) {
        return new LocalidadeDTO(
                entity.getId(),
                entity.getCidade(),
                entity.getEstado()
        );
    }
    private Localidade toEntity(LocalidadeDTO dto) {
        Localidade entity = new Localidade();
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        return entity;
    }
}

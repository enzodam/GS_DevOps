package br.com.fiap.sosclimatech.service;
import br.com.fiap.sosclimatech.dto.PessoaAfetadaRequestDTO;
import br.com.fiap.sosclimatech.dto.PessoaAfetadaResponseDTO;
import br.com.fiap.sosclimatech.exception.ResourceNotFoundException;
import br.com.fiap.sosclimatech.model.EventoClimatico;
import br.com.fiap.sosclimatech.model.PessoaAfetada;
import br.com.fiap.sosclimatech.model.StatusAssistencia;
import br.com.fiap.sosclimatech.repository.PessoaAfetadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PessoaAfetadaService {
    private final PessoaAfetadaRepository pessoaAfetadaRepository;
    private final EventoClimaticoService eventoClimaticoService; 
    @Autowired
    public PessoaAfetadaService(PessoaAfetadaRepository pessoaAfetadaRepository, EventoClimaticoService eventoClimaticoService) {
        this.pessoaAfetadaRepository = pessoaAfetadaRepository;
        this.eventoClimaticoService = eventoClimaticoService;
    }
    @Transactional(readOnly = true)
    public List<PessoaAfetadaResponseDTO> findAll() {
        List<PessoaAfetada> pessoas = pessoaAfetadaRepository.findAll();
        return pessoas.stream()
                .map(PessoaAfetadaResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public PessoaAfetadaResponseDTO findById(Long id) {
        PessoaAfetada pessoa = pessoaAfetadaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa Afetada não encontrada com ID: " + id));
        return new PessoaAfetadaResponseDTO(pessoa);
    }
    @Transactional
    public PessoaAfetadaResponseDTO save(PessoaAfetadaRequestDTO pessoaDTO) {
        EventoClimatico evento = eventoClimaticoService.findEntityById(pessoaDTO.eventoId());
        PessoaAfetada pessoa = new PessoaAfetada();
        mapDtoToEntity(pessoaDTO, pessoa, evento);
        pessoa.setStatusAssistencia(StatusAssistencia.REGISTRADO); 
        pessoa = pessoaAfetadaRepository.save(pessoa);
        return new PessoaAfetadaResponseDTO(pessoa);
    }
    @Transactional
    public PessoaAfetadaResponseDTO update(Long id, PessoaAfetadaRequestDTO pessoaDTO) {
        PessoaAfetada pessoa = pessoaAfetadaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa Afetada não encontrada com ID: " + id));
        EventoClimatico evento = eventoClimaticoService.findEntityById(pessoaDTO.eventoId());
        mapDtoToEntity(pessoaDTO, pessoa, evento);
        pessoa = pessoaAfetadaRepository.save(pessoa);
        return new PessoaAfetadaResponseDTO(pessoa);
    }
    @Transactional
    public PessoaAfetadaResponseDTO updateStatus(Long id, StatusAssistencia novoStatus) {
        PessoaAfetada pessoa = pessoaAfetadaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa Afetada não encontrada com ID: " + id));
        pessoa.setStatusAssistencia(novoStatus);
        pessoa = pessoaAfetadaRepository.save(pessoa);
        return new PessoaAfetadaResponseDTO(pessoa);
    }
    @Transactional
    public void delete(Long id) {
        if (!pessoaAfetadaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa Afetada não encontrada com ID: " + id);
        }
        pessoaAfetadaRepository.deleteById(id);
    }
    private void mapDtoToEntity(PessoaAfetadaRequestDTO dto, PessoaAfetada entity, EventoClimatico evento) {
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf()); 
        entity.setEndereco(dto.endereco());
        entity.setEvento(evento);
    }
}

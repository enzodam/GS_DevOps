package br.com.fiap.sosclimatech.service;

import br.com.fiap.sosclimatech.dto.RegistroAjudaDTO;
import br.com.fiap.sosclimatech.exception.ResourceNotFoundException;
import br.com.fiap.sosclimatech.model.*;
import br.com.fiap.sosclimatech.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroAjudaService {

    private final RegistroAjudaRepository registroRepository;
    private final PessoaAfetadaRepository pessoaRepository;
    private final RecursoRepository recursoRepository;

    @Autowired
    public RegistroAjudaService(RegistroAjudaRepository registroRepository, PessoaAfetadaRepository pessoaRepository, RecursoRepository recursoRepository) {
        this.registroRepository = registroRepository;
        this.pessoaRepository = pessoaRepository;
        this.recursoRepository = recursoRepository;
    }

    @Transactional(readOnly = true)
    public List<RegistroAjudaDTO> findAll() {
        return registroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistroAjudaDTO findById(Long id) {
        RegistroAjuda registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de ajuda não encontrado com ID: " + id));
        return toDTO(registro);
    }

    @Transactional
    public RegistroAjudaDTO save(RegistroAjudaDTO dto) {
        PessoaAfetada pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + dto.getPessoaId()));
        Recurso recurso = recursoRepository.findById(dto.getRecursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com ID: " + dto.getRecursoId()));

        RegistroAjuda registro = new RegistroAjuda();
        registro.setPessoa(pessoa);
        registro.setRecurso(recurso);
        registro.setQuantidade(dto.getQuantidade());
        registro.setDataRegistro(LocalDateTime.now());
        registro.setEntregue(false);

        registro = registroRepository.save(registro);
        return toDTO(registro);
    }

    @Transactional
    public RegistroAjudaDTO marcarComoEntregue(Long id) {
        RegistroAjuda registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de ajuda não encontrado com ID: " + id));
        registro.setEntregue(true);
        registro = registroRepository.save(registro);
        return toDTO(registro);
    }

    @Transactional
    public void delete(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de ajuda não encontrado com ID: " + id);
        }
        registroRepository.deleteById(id);
    }

    private RegistroAjudaDTO toDTO(RegistroAjuda entity) {
        return new RegistroAjudaDTO(
                entity.getId(),
                entity.getPessoa().getId(),
                entity.getRecurso().getId(),
                entity.getQuantidade(),
                entity.getDataRegistro(),
                entity.isEntregue()
        );
    }
}


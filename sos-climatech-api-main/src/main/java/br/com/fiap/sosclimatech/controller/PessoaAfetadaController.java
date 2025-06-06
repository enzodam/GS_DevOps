package br.com.fiap.sosclimatech.controller;
import br.com.fiap.sosclimatech.dto.PessoaAfetadaRequestDTO;
import br.com.fiap.sosclimatech.dto.PessoaAfetadaResponseDTO;
import br.com.fiap.sosclimatech.exception.ApiError;
import br.com.fiap.sosclimatech.model.StatusAssistencia;
import br.com.fiap.sosclimatech.service.PessoaAfetadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas Afetadas", description = "Endpoints para gerenciamento de pessoas afetadas por eventos climáticos")
public class PessoaAfetadaController {
    private final PessoaAfetadaService pessoaAfetadaService;
    @Autowired
    public PessoaAfetadaController(PessoaAfetadaService pessoaAfetadaService) {
        this.pessoaAfetadaService = pessoaAfetadaService;
    }
    @Operation(summary = "Lista todas as pessoas afetadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PessoaAfetadaResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PessoaAfetadaResponseDTO>> listarTodos() {
        List<PessoaAfetadaResponseDTO> pessoas = pessoaAfetadaService.findAll();
        return ResponseEntity.ok(pessoas);
    }
    @Operation(summary = "Busca uma pessoa afetada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PessoaAfetadaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaAfetadaResponseDTO> buscarPorId(@PathVariable Long id) {
        PessoaAfetadaResponseDTO pessoa = pessoaAfetadaService.findById(id);
        return ResponseEntity.ok(pessoa);
    }
    @Operation(summary = "Cadastra uma nova pessoa afetada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PessoaAfetadaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Evento climático relacionado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<PessoaAfetadaResponseDTO> cadastrar(@Valid @RequestBody PessoaAfetadaRequestDTO pessoaDTO) {
        PessoaAfetadaResponseDTO novaPessoa = pessoaAfetadaService.save(pessoaDTO);
        return new ResponseEntity<>(novaPessoa, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza uma pessoa afetada existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PessoaAfetadaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Pessoa ou Evento climático relacionado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PessoaAfetadaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaAfetadaRequestDTO pessoaDTO) {
        PessoaAfetadaResponseDTO pessoaAtualizada = pessoaAfetadaService.update(id, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }
    @Operation(summary = "Atualiza o status de assistência de uma pessoa afetada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PessoaAfetadaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<PessoaAfetadaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Parameter(description = "Novo status de assistência", required = true, schema = @Schema(implementation = StatusAssistencia.class))
            @RequestParam StatusAssistencia status) {
        PessoaAfetadaResponseDTO pessoaAtualizada = pessoaAfetadaService.updateStatus(id, status);
        return ResponseEntity.ok(pessoaAtualizada);
    }
    @Operation(summary = "Remove uma pessoa afetada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa removida com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"Pessoa afetada deletada com sucesso\"}"))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable Long id) {
        pessoaAfetadaService.delete(id);
        Map<String, String> response = Map.of("message", "Pessoa afetada deletada com sucesso");
        return ResponseEntity.ok(response);
    }
}

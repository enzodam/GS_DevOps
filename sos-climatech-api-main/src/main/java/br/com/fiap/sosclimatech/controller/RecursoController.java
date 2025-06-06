package br.com.fiap.sosclimatech.controller;
import br.com.fiap.sosclimatech.dto.RecursoDTO;
import br.com.fiap.sosclimatech.exception.ApiError;
import br.com.fiap.sosclimatech.service.RecursoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/recursos")
@Tag(name = "Recursos", description = "Endpoints para gerenciamento de recursos")
public class RecursoController {
    private final RecursoService service;
    @Autowired
    public RecursoController(RecursoService service) {
        this.service = service;
    }
    @Operation(summary = "Lista todos os recursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recursos encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<RecursoDTO>> findAll() {
        List<RecursoDTO> recursos = service.findAll();
        return ResponseEntity.ok(recursos);
    }
    @Operation(summary = "Busca um recurso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> findById(@PathVariable Long id) {
        RecursoDTO recurso = service.findById(id);
        return ResponseEntity.ok(recurso);
    }
    @Operation(summary = "Cadastra um novo recurso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Evento climático relacionado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<RecursoDTO> create(@Valid @RequestBody RecursoDTO dto) {
        RecursoDTO novoRecurso = service.save(dto);
        return new ResponseEntity<>(novoRecurso, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza um recurso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Recurso ou Evento climático relacionado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> update(@PathVariable Long id, @Valid @RequestBody RecursoDTO dto) {
        RecursoDTO recursoAtualizado = service.update(id, dto);
        return ResponseEntity.ok(recursoAtualizado);
    }
    @Operation(summary = "Remove um recurso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso removido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"Recurso deletado com sucesso\"}"))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, String> response = Map.of("message", "Recurso deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}

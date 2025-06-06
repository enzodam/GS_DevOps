package br.com.fiap.sosclimatech.controller;
import br.com.fiap.sosclimatech.dto.RegistroAjudaDTO;
import br.com.fiap.sosclimatech.exception.ApiError;
import br.com.fiap.sosclimatech.service.RegistroAjudaService;
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
@RequestMapping("/api/registros-ajuda")
@Tag(name = "Registros de Ajuda", description = "Endpoints para gerenciamento de registros de ajuda")
public class RegistroAjudaController {
    private final RegistroAjudaService service;
    @Autowired
    public RegistroAjudaController(RegistroAjudaService service) {
        this.service = service;
    }
    @Operation(summary = "Lista todos os registros de ajuda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroAjudaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<RegistroAjudaDTO>> findAll() {
        List<RegistroAjudaDTO> registros = service.findAll();
        return ResponseEntity.ok(registros);
    }
    @Operation(summary = "Busca um registro de ajuda por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroAjudaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistroAjudaDTO> findById(@PathVariable Long id) {
        RegistroAjudaDTO registro = service.findById(id);
        return ResponseEntity.ok(registro);
    }
    @Operation(summary = "Cadastra um novo registro de ajuda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroAjudaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Pessoa afetada ou Recurso relacionado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<RegistroAjudaDTO> create(@Valid @RequestBody RegistroAjudaDTO dto) {
        RegistroAjudaDTO novoRegistro = service.save(dto);
        return new ResponseEntity<>(novoRegistro, HttpStatus.CREATED);
    }
    @Operation(summary = "Marca um registro de ajuda como entregue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro marcado como entregue com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroAjudaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}/entregue")
    public ResponseEntity<RegistroAjudaDTO> marcarComoEntregue(@PathVariable Long id) {
        RegistroAjudaDTO registroAtualizado = service.marcarComoEntregue(id);
        return ResponseEntity.ok(registroAtualizado);
    }
    @Operation(summary = "Remove um registro de ajuda por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de ajuda removido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"Registro de ajuda deletado com sucesso\"}"))),
            @ApiResponse(responseCode = "404", description = "Registro de ajuda não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, String> response = Map.of("message", "Registro de ajuda deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}


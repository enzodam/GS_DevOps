package br.com.fiap.sosclimatech.controller;
import br.com.fiap.sosclimatech.dto.LocalidadeDTO;
import br.com.fiap.sosclimatech.exception.ApiError;
import br.com.fiap.sosclimatech.service.LocalidadeService;
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
@RequestMapping("/api/localidades")
@Tag(name = "Localidades", description = "Endpoints para gerenciamento de localidades")
public class LocalidadeController {
    private final LocalidadeService service;
    @Autowired
    public LocalidadeController(LocalidadeService service) {
        this.service = service;
    }
    @Operation(summary = "Lista todas as localidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de localidades encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalidadeDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<LocalidadeDTO>> findAll() {
        List<LocalidadeDTO> localidades = service.findAll();
        return ResponseEntity.ok(localidades);
    }
    @Operation(summary = "Busca uma localidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localidade encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalidadeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Localidade não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocalidadeDTO> findById(@PathVariable Long id) {
        LocalidadeDTO localidade = service.findById(id);
        return ResponseEntity.ok(localidade);
    }
    @Operation(summary = "Cadastra uma nova localidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Localidade cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalidadeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object")))
    })
    @PostMapping
    public ResponseEntity<LocalidadeDTO> create(@Valid @RequestBody LocalidadeDTO dto) {
        LocalidadeDTO novaLocalidade = service.save(dto);
        return new ResponseEntity<>(novaLocalidade, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza uma localidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localidade atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalidadeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Localidade não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalidadeDTO> update(@PathVariable Long id, @Valid @RequestBody LocalidadeDTO dto) {
        LocalidadeDTO localidadeAtualizada = service.update(id, dto);
        return ResponseEntity.ok(localidadeAtualizada);
    }
    @Operation(summary = "Remove uma localidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localidade removida com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"Localidade deletada com sucesso\"}"))),
            @ApiResponse(responseCode = "404", description = "Localidade não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, String> response = Map.of("message", "Localidade deletada com sucesso");
        return ResponseEntity.ok(response);
    }
}

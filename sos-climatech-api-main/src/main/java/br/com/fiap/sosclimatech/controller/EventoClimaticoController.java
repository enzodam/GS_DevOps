package br.com.fiap.sosclimatech.controller;
import br.com.fiap.sosclimatech.dto.EventoClimaticoRequestDTO;
import br.com.fiap.sosclimatech.dto.EventoClimaticoResponseDTO;
import br.com.fiap.sosclimatech.exception.ApiError;
import br.com.fiap.sosclimatech.service.EventoClimaticoService;
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
@RequestMapping("/api/eventos")
@Tag(name = "Eventos Climáticos", description = "Endpoints para gerenciamento de eventos climáticos")
public class EventoClimaticoController {
    private final EventoClimaticoService eventoClimaticoService;
    @Autowired
    public EventoClimaticoController(EventoClimaticoService eventoClimaticoService) {
        this.eventoClimaticoService = eventoClimaticoService;
    }
    @Operation(summary = "Lista todos os eventos climáticos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoClimaticoResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<EventoClimaticoResponseDTO>> listarTodos() {
        List<EventoClimaticoResponseDTO> eventos = eventoClimaticoService.findAll();
        return ResponseEntity.ok(eventos);
    }
    @Operation(summary = "Busca um evento climático por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoClimaticoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventoClimaticoResponseDTO> buscarPorId(@PathVariable Long id) {
        EventoClimaticoResponseDTO evento = eventoClimaticoService.findById(id);
        return ResponseEntity.ok(evento);
    }
    @Operation(summary = "Cadastra um novo evento climático")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoClimaticoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object")))
    })
    @PostMapping
    public ResponseEntity<EventoClimaticoResponseDTO> cadastrar(@Valid @RequestBody EventoClimaticoRequestDTO eventoDTO) {
        EventoClimaticoResponseDTO novoEvento = eventoClimaticoService.save(eventoDTO);
        return new ResponseEntity<>(novoEvento, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza um evento climático existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoClimaticoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object"))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventoClimaticoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EventoClimaticoRequestDTO eventoDTO) {
        EventoClimaticoResponseDTO eventoAtualizado = eventoClimaticoService.update(id, eventoDTO);
        return ResponseEntity.ok(eventoAtualizado);
    }
    @Operation(summary = "Remove um evento climático por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento removido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "object", example = "{\"message\": \"Evento climático deletado com sucesso\"}"))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable Long id) {
        eventoClimaticoService.delete(id);
        Map<String, String> response = Map.of("message", "Evento climático deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}


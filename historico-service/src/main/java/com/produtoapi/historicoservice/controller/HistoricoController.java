package com.produtoapi.historicoservice.controller;
import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/eventos")
public class HistoricoController {

    private final HistoricoProdutoService historicoProdutoService;


    @PostMapping("/produto")
    public ResponseEntity<HistoricoProdutoResponseDTO> salvar(@RequestBody HistoricoProdutoRequestDTO dto) {
        return ResponseEntity.ok(historicoProdutoService.salvar(dto));
    }

    @GetMapping("/historico")
    public ResponseEntity<ApiResponse<List<HistoricoProdutoResponseDTO>>> listaPorPeriodo(@RequestParam(required = false) LocalDateTime inicio, @RequestParam(required = false) LocalDateTime fim) {

        List<HistoricoProdutoResponseDTO> historico = historicoProdutoService.buscarPorPeriodo(inicio, fim);

        ApiResponse<List<HistoricoProdutoResponseDTO>> response = new ApiResponse<>("Busca realizada com sucesso", historico);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HistoricoProdutoResponseDTO>> listaPorID(@PathVariable Long id) {

        HistoricoProdutoResponseDTO historico = historicoProdutoService.listarPorid(id);
        ApiResponse<HistoricoProdutoResponseDTO> response = new ApiResponse<>("Sua busca por id foi requisitada com sucesso", historico);

        return ResponseEntity.ok(response);

    }

}

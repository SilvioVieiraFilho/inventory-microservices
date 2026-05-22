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
public class HistoricoProdutoController {

    private final HistoricoProdutoService historicoProdutoService;




    @GetMapping("/historico")
    public ResponseEntity<ApiResponse<List<HistoricoProdutoResponseDTO>>> listaPorPeriodo(@RequestParam(required = false) LocalDateTime inicio, @RequestParam(required = false) LocalDateTime fim) {

        List<HistoricoProdutoResponseDTO> historico = historicoProdutoService.buscarPorPeriodo(inicio, fim);

        ApiResponse<List<HistoricoProdutoResponseDTO>> response = new ApiResponse<>("Busca realizada com sucesso", historico);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HistoricoProdutoResponseDTO>>
    buscarPorId(@PathVariable Long id) {

        HistoricoProdutoResponseDTO historico =
                historicoProdutoService.buscarPorId(id);

        ApiResponse<HistoricoProdutoResponseDTO> response =
                new ApiResponse<>(
                        "Histórico encontrado com sucesso",
                        historico
                );

        return ResponseEntity.ok(response);
    }
}

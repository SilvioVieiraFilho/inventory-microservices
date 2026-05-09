package com.produtoapi.historicoservice.contoller;



import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.service.HistoricoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/eventos")
public class HistoricoController {

    private final HistoricoProdutoService  historicoprodutoService;

    @PostMapping("/produto")
    public ResponseEntity<Void> registrar(@RequestBody HistoricoProdutoRequestDTO dto) {
        historicoprodutoService.salvar(dto);
        return ResponseEntity.ok().build();
    }
    }

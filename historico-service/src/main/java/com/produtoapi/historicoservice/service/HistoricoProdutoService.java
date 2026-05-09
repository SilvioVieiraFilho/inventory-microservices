package com.produtoapi.historicoservice.service;

import com.produtoapi.historicoservice.ProdutoClient;
import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.dto.ProdutoDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.mapper.HistoricoProdutoMapper;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoricoProdutoService {

    private final HistoricoProdutoRepository repository;
    private final HistoricoProdutoMapper mapper;
    private final ProdutoClient produtoClient;

    public HistoricoProduto salvar(HistoricoProdutoRequestDTO dto) {

        if (dto.getTipoEvento() == null) {
            throw new RuntimeException("TipoEvento não pode ser nulo");
        }

        HistoricoProduto historico = new HistoricoProduto();

        historico.setProdutoId(dto.getProdutoId());
        historico.setNomeProduto(dto.getNomeProduto());
        historico.setQuantidadeAnterior(dto.getQuantidadeAnterior());
        historico.setQuantidadeNova(dto.getQuantidadeNova());
        historico.setDiferenca(dto.getDiferenca());
        historico.setTipoEvento(dto.getTipoEvento());
        historico.setDataRegistro(LocalDateTime.now());

        return repository.save(historico);
    }

//    public List<HistoricoProdutoResponseDTO>
//    buscarPorProduto(Long produtoId){
//
//
//    }
//
//    public List<HistoricoProdutoResponseDTO> listarTodos(){
//
//    }
//
//
//    public List<HistoricoProdutoResponseDTO>
//    buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim){
//
//
//    }
//
//
//    buscarPorEvento(TipoEvento evento){
//
//
//
//    }

}






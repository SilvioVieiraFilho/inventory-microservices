package com.produtoapi.historicoservice.service;

import com.produtoapi.historicoservice.dto.HistoricoProdutoRequestDTO;
import com.produtoapi.historicoservice.dto.HistoricoProdutoResponseDTO;
import com.produtoapi.historicoservice.entity.HistoricoProduto;
import com.produtoapi.historicoservice.exception.HistoricoNotFoundException;
import com.produtoapi.historicoservice.mapper.HistoricoProdutoMapper;
import com.produtoapi.historicoservice.repository.HistoricoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricoProdutoService {

    private final HistoricoProdutoMapper mapper;
    private final HistoricoProdutoRepository repository;
    public HistoricoProdutoResponseDTO salvar(HistoricoProdutoRequestDTO dto) {

        if (dto.getTipoEvento() == null) {
            throw new RuntimeException("TipoEvento não pode ser nulo");
        }

        if (dto.getQuantidadeAnterior() == null ||
                dto.getQuantidadeNova() == null) {

            throw new RuntimeException("Quantidades não podem ser nulas");
        }



        HistoricoProduto historico = mapper.toEntity(dto);

        historico.setDiferenca(
                dto.getQuantidadeNova() - dto.getQuantidadeAnterior()
        );

        historico.setDataRegistro(LocalDateTime.now());

        HistoricoProduto historicoSalvo = repository.save(historico);

        return mapper.toDTO(historicoSalvo);

    }
    public HistoricoProdutoResponseDTO buscarPorId(Long id) {

        HistoricoProduto historico =
                repository.findById(id)
                        .orElseThrow(() ->
                                new HistoricoNotFoundException(id));

        return mapper.toDTO(historico);
    }


    private HistoricoProduto buscarOuFalhar (Long id){
        return repository.findById(id)
                .orElseThrow(() -> new  HistoricoNotFoundException(id));
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

    public List<HistoricoProdutoResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {

        if (inicio != null && fim != null && inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Data de início não pode ser maior que a data fim.");
        }

        return repository.findByDataRegistroBetween(inicio, fim)
                .stream()
                .map(mapper::toDTO)
                .toList();

     }

}







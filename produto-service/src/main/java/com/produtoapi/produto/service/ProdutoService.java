package com.produtoapi.produto.service;

import java.util.List;
import java.util.Optional;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.exception.ProdutoNotFoundException;
import com.produtoapi.historicoproduto.client.HistoricoClient;
import com.produtoapi.historicoproduto.service.HistoricoIntegrationService;
import com.produtoapi.historicoproduto.enums.TipoEvento;
import com.produtoapi.produto.dto.ProdutoEventoDTO;
import com.produtoapi.produto.producer.ProdutoProducer;
import com.produtoapi.produto.specification.ProdutoSpecification;
import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.domain.ProdutoDomainService;
import com.produtoapi.produto.domain.ProdutoFactory;
import com.produtoapi.produto.dto.ProdutoResponseDTO;
import com.produtoapi.produto.mapper.ProdutoMapper;


import com.produtoapi.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.produtoapi.produto.dto.ProdutoRequestDTO;
import com.produtoapi.produto.enums.ProdutoStatus;
import com.produtoapi.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final HistoricoClient historicoClient;
    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final ProdutoFactory factory;
    private final ProdutoDomainService domain;

    private final ProdutoProducer producer;
    private final CategoriaRepository categoriaRepository;

    private final HistoricoIntegrationService historicoIntegrationService;

    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        Optional<Produto> produtoExistente =
                repository.findByNomeAndPrecoAndStatus(
                        dto.getNome(),
                        dto.getPreco(),
                        dto.getStatus()
                );

        boolean novoProduto = produtoExistente.isEmpty();

        int quantidadeAnterior =
                produtoExistente
                        .map(Produto::getQuantidade)
                        .orElse(0);

        Produto produto = produtoExistente
                .map(p -> atualizarExistente(p, dto))
                .orElseGet(() -> criarNovo(dto));

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoria_id())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);


        Produto produtoSalvo = repository.save(produto);

        ProdutoEventoDTO evento = ProdutoEventoDTO.builder()
                .produtoId(produtoSalvo.getId())
                .nome(produtoSalvo.getNome())
                .preco(produtoSalvo.getPreco())
                .quantidadeAnterior(quantidadeAnterior)
                .quantidadeNova(produtoSalvo.getQuantidade())
                .tipoEvento(
                        novoProduto
                                ? TipoEvento.PRODUTO_CRIADO
                                : TipoEvento.PRODUTO_ATUALIZADO
                )


                .build();





        producer.enviarEvento(evento);

        return mapper.toDTO(produtoSalvo);
    }

        private Produto atualizarExistente (Produto produto, ProdutoRequestDTO dto){
            domain.atualizar(produto, dto.getQuantidade());
            return produto;
        }

        private Produto criarNovo (ProdutoRequestDTO dto){
            Produto produto = factory.criar(dto);
            domain.inicializar(produto);
            return produto;
        }

        public void deletarProduto (Long id){
            repository.delete(buscarOuFalhar(id));
        }


        public ProdutoResponseDTO atualizarProduto (Long id, ProdutoRequestDTO dto){

            Produto produto = buscarOuFalhar(id);

            domain.atualizarDadosBasicos(
                    produto,
                    dto.getNome(),
                    dto.getPreco(),
                    dto.getQuantidade()
            );

            return mapper.toDTO(repository.save(produto));
        }


        public ProdutoResponseDTO buscarPorId (Long id){
            return mapper.toDTO(buscarOuFalhar(id));
        }

        private Produto buscarOuFalhar (Long id){
            return repository.findById(id)
                    .orElseThrow(() -> new ProdutoNotFoundException(id));
        }


        public List<ProdutoResponseDTO> salvarLista (List < ProdutoRequestDTO > listaDTO) {
            return listaDTO.stream()
                    .map(this::salvar)
                    .toList();
        }


        public List<ProdutoResponseDTO> buscarFiltro (
                String nome,
                ProdutoStatus status,
                Double precoMin,
                Double precoMax
    ){

            if (nome == null && status == null && precoMin == null && precoMax == null) {
                throw new BusinessException("Informe pelo menos um filtro");
            }

            if (precoMin != null && precoMax != null && precoMin > precoMax) {
                Double temp = precoMin;
                precoMin = precoMax;
                precoMax = temp;
            }

            Specification<Produto> spec = Specification.where(null);

            if (nome != null) {
                spec = spec.and(ProdutoSpecification.nome(nome));
            }

            if (status != null) {
                spec = spec.and(ProdutoSpecification.status(status));
            }

            if (precoMin != null) {
                spec = spec.and(ProdutoSpecification.minPreco(precoMin));
            }

            if (precoMax != null) {
                spec = spec.and(ProdutoSpecification.maxPreco(precoMax));
            }


            List<Produto> produtos = repository.findAll(spec);

            if (produtos.isEmpty()) {
                throw new BusinessException("Nenhum produto encontrado");
            }

            return produtos.stream()
                    .map(mapper::toDTO)
                    .toList();
        }


    }

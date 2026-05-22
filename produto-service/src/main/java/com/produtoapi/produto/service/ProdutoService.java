package com.produtoapi.produto.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.produtoapi.produto.enums.TipoEvento;
import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.exception.ProdutoNotFoundException;
import com.produtoapi.produto.dto.ProdutoEventoDTO;
import com.produtoapi.produto.producer.ProdutoProducer;
import com.produtoapi.produto.specification.ProdutoSpecification;
import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.domain.ProdutoDomainService;
import com.produtoapi.produto.domain.ProdutoFactory;
import com.produtoapi.produto.dto.ProdutoResponseDTO;
import com.produtoapi.produto.mapper.ProdutoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final ProdutoFactory factory;
    private final ProdutoDomainService domain;
    private final ProdutoProducer producer;
    private final CategoriaRepository categoriaRepository;

    public Page<ProdutoResponseDTO> listar(String nome, Pageable pageable) {

        Specification<Produto> spec = Specification.where(null);

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(ProdutoSpecification.nomeContains(nome));
        }

        return repository.findAll(spec, pageable)
                .map(ProdutoResponseDTO::new);
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {

        domain.validarCadastro(dto);

        Categoria categoria = buscarCategoriaOuFalhar(dto.getCategoria_id());

        Optional<Produto> produtoExistente =
                repository.findByNomeAndCategoria_Id(dto.getNome(), dto.getCategoria_id());

        Produto produto;
        int quantidadeAnterior;

        if (produtoExistente.isPresent()) {

            produto = produtoExistente.get();

            quantidadeAnterior = produto.getQuantidade();

            domain.atualizarProduto(produto, dto);

            domain.adicionarQuantidade(produto, dto.getQuantidade());

        } else {

            produto = factory.criar(dto);

            domain.inicializar(produto);

            quantidadeAnterior = 0;
        }

        produto.setCategoria(categoria);

        Produto produtoSalvo = repository.save(produto);

        publicarEvento(
                produtoSalvo,
                quantidadeAnterior,
                produtoExistente.isPresent()
                        ? TipoEvento.PRODUTO_ATUALIZADO
                        : TipoEvento.PRODUTO_CRIADO
        );

        return mapper.toDTO(produtoSalvo);
    }
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {

        Produto produto = buscarOuFalhar(id);

        int quantidadeAnterior = produto.getQuantidade();

        domain.atualizarProduto(produto, dto);

        produto.setCategoria(buscarCategoriaOuFalhar(dto.getCategoria_id()));

        Produto produtoSalvo = repository.save(produto);

        publicarEvento(
                produtoSalvo,
                quantidadeAnterior,
                TipoEvento.PRODUTO_ATUALIZADO
        );

        return mapper.toDTO(produtoSalvo);
    }

    public void deletarProduto(Long id) {
        repository.delete(buscarOuFalhar(id));
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarOuFalhar(id));
    }

    public List<ProdutoResponseDTO> salvarLista(List<ProdutoRequestDTO> listaDTO) {
        return listaDTO.stream()
                .map(this::salvar)
                .toList();
    }

    public List<ProdutoResponseDTO> buscarFiltro(
            String nome,
            ProdutoStatus status,
            Double precoMin,
            Double precoMax
    ) {

        domain.validarFiltro(nome, status, precoMin, precoMax);

        Specification<Produto> spec = Specification
                .where(ProdutoSpecification.nome(nome))
                .and(ProdutoSpecification.status(status))
                .and(ProdutoSpecification.minPreco(precoMin))
                .and(ProdutoSpecification.maxPreco(precoMax));

        List<Produto> produtos = repository.findAll(spec);

        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado");
        }

        return produtos.stream()
                .map(mapper::toDTO)
                .toList();
    }


    private Categoria buscarCategoriaOuFalhar(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new BusinessException("Categoria não encontrada"));
    }

    private Produto buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNotFoundException(id));
    }

    private void publicarEvento(
            Produto produto,
            int quantidadeAnterior,
            TipoEvento tipoEvento
    ) {

        ProdutoEventoDTO evento = ProdutoEventoDTO.builder()
                .eventId(UUID.randomUUID())
                .produtoId(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .quantidadeAnterior(quantidadeAnterior)
                .quantidadeNova(produto.getQuantidade())
                .tipoEvento(tipoEvento)
                .build();

        producer.enviarEvento(evento);
    }
}

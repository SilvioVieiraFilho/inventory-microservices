package com.produtoapi.produtos;

import com.produtoapi.categoria.domain.Categoria;
import com.produtoapi.categoria.repository.CategoriaRepository;
import com.produtoapi.exception.BusinessException;
import com.produtoapi.produto.domain.Produto;
import com.produtoapi.produto.domain.ProdutoDomainService;
import com.produtoapi.produto.domain.ProdutoFactory;
import com.produtoapi.produto.dto.ProdutoRequestDTO;
import com.produtoapi.produto.dto.ProdutoResponseDTO;
import com.produtoapi.produto.enums.ProdutoStatus;
import com.produtoapi.produto.mapper.ProdutoMapper;
import com.produtoapi.produto.producer.ProdutoProducer;
import com.produtoapi.produto.repository.ProdutoRepository;
import com.produtoapi.produto.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @Mock
    private ProdutoMapper mapper;

    @Mock
    private ProdutoFactory factory;

    @Mock
    private ProdutoDomainService domain;

    @Mock
    private ProdutoProducer producer;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void deveSalvarNovoProduto() {

        ProdutoRequestDTO dto = criarDTO();

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidade(10);

        ProdutoResponseDTO response = mock(ProdutoResponseDTO.class);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.findByNomeAndCategoria_Id(dto.getNome(), 1L))
                .thenReturn(Optional.empty());
        when(factory.criar(dto)).thenReturn(produto);
        when(repository.save(produto)).thenReturn(produto);
        when(mapper.toDTO(produto)).thenReturn(response);

        ProdutoResponseDTO result = service.salvar(dto);

        assertNotNull(result);

        verify(domain).validarCadastro(dto);
        verify(domain).inicializar(produto);
        verify(producer).enviarEvento(any());
    }

    @Test
    void deveAtualizarProdutoExistente() {

        ProdutoRequestDTO dto = criarDTO();

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidade(5);

        ProdutoResponseDTO response = mock(ProdutoResponseDTO.class);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.findByNomeAndCategoria_Id(dto.getNome(), 1L))
                .thenReturn(Optional.of(produto));
        when(repository.save(produto)).thenReturn(produto);
        when(mapper.toDTO(produto)).thenReturn(response);

        ProdutoResponseDTO result = service.salvar(dto);

        assertNotNull(result);

        verify(domain).atualizarProduto(produto, dto);
        verify(domain).adicionarQuantidade(produto, dto.getQuantidade());
        verify(producer).enviarEvento(any());
    }

    @Test
    void deveAtualizarProdutoPorId() {

        ProdutoRequestDTO dto = criarDTO();

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidade(10);

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.save(produto)).thenReturn(produto);

        ProdutoResponseDTO response = mock(ProdutoResponseDTO.class);
        when(mapper.toDTO(produto)).thenReturn(response);

        ProdutoResponseDTO result = service.atualizarProduto(1L, dto);

        assertNotNull(result);

        verify(domain).atualizarProduto(produto, dto);
        verify(producer).enviarEvento(any());
    }

    @Test
    void deveDeletarProduto() {

        Produto produto = new Produto();
        produto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        service.deletarProduto(1L);

        verify(repository).delete(produto);
    }

    @Test
    void deveBuscarPorId() {

        Produto produto = new Produto();
        produto.setId(1L);

        ProdutoResponseDTO response = mock(ProdutoResponseDTO.class);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(mapper.toDTO(produto)).thenReturn(response);

        ProdutoResponseDTO result = service.buscarPorId(1L);

        assertNotNull(result);
    }

    @Test
    void deveBuscarFiltro() {

        Produto produto = new Produto();
        ProdutoResponseDTO response = mock(ProdutoResponseDTO.class);

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of(produto));

        when(mapper.toDTO(produto))
                .thenReturn(response);

        List<ProdutoResponseDTO> result =
                service.buscarFiltro("teste", ProdutoStatus.ATIVO, null, null);

        assertFalse(result.isEmpty());

        verify(domain).validarFiltro("teste", ProdutoStatus.ATIVO, null, null);
    }

    @Test
    void deveFalharFiltroVazio() {

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        assertThrows(BusinessException.class, () ->
                service.buscarFiltro("x", null, null, null)
        );
    }

    private ProdutoRequestDTO criarDTO() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO();
        dto.setNome("Produto");
        dto.setPreco(100.0);
        dto.setQuantidade(10);
        dto.setStatus(ProdutoStatus.ATIVO);
        dto.setCategoria_id(1L);
        return dto;
    }
}
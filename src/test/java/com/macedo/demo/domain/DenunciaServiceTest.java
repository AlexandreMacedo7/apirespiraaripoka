package com.macedo.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;

@ExtendWith(MockitoExtension.class)
public class DenunciaServiceTest {

    @InjectMocks
    private DenunciaService denunciaService;

    @Mock
    private DenunciaRepository denunciaRepository;

    @Mock
    private DenunciaMapper denunciaMapper;

    @Test
    public void criaDenuncia_ComDadosValidos_RetornaDto() {

        // Arrange
        CriarDenunciaDtoRequest dtoRequest = criarCriarDenunciaDtoRequest();
        Denuncia denunciaSalva = criarDenuncia();
        DenunciaDtoResponse dtoResponseSimulado = criarDenunciaDtoResponse();

        when(denunciaMapper.toEntity(dtoRequest)).thenReturn(denunciaSalva);

        when(denunciaRepository.save(any(Denuncia.class))).thenReturn(denunciaSalva);

        when(denunciaMapper.toDto(denunciaSalva)).thenReturn(dtoResponseSimulado);

        // Act
        DenunciaDtoResponse dtoResponseReal = denunciaService.create(dtoRequest);

        // Assert
        assertThat(dtoResponseSimulado).isEqualTo(dtoResponseReal);
    }

    @Test
    public void criaDenuncia_ComDadosInvalidos_RetornaExcecao() {

        // Arrange

        CriarDenunciaDtoRequest dtoInvalidoRequest = criarCriarDenunciaDtoInvalidoRequest();

        when(denunciaMapper.toEntity(dtoInvalidoRequest)).thenThrow(RuntimeException.class);

        // Act & Assert

        assertThatThrownBy(() -> denunciaService.create(dtoInvalidoRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getDenunciaId_Existente_RetornaDto() {

        // Arrange
        Long id = 1L;
        Denuncia denuncia = criarDenuncia();
        ConsultaDenunciaDtoResponse dtoResponseSimulado = criaConsultaDenunciaDtoResponse();

        when(denunciaRepository.findById(1l)).thenReturn(Optional.of(denuncia));
        when(denunciaMapper.toDtoConsulta(denuncia)).thenReturn(dtoResponseSimulado);

        // Act
        ConsultaDenunciaDtoResponse dtoResponseReal = denunciaService.getDenunciaById(id);

        // Assert

        assertEquals(dtoResponseSimulado, dtoResponseReal);

    }

    @Test
    public void getDenunciaId_Inexistente_RetornaExcecao() {

        // Arrange
        Long id = 1L;
        when(denunciaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> denunciaService.getDenunciaById(id)).isInstanceOf(RuntimeException.class);
    }
    
    @Test
    public void getAllDenuncia_DeveRetornarPaginaDeDtoResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        List<Denuncia> denuncias = criarDenuncias();
        Page<Denuncia> paginaDenuncias = new PageImpl<>(denuncias, pageable,
                denuncias.size());

        when(denunciaRepository.findAll(pageable)).thenReturn(paginaDenuncias);

        List<DenunciaDtoResponse> dtoResponsesSimulados = denuncias.stream()
                .map(denunciaMapper::toDto)
                .collect(Collectors.toList());

        // Act
        Page<DenunciaDtoResponse> resultado = denunciaService.getAllDenuncia(pageable);

        // Assert
        assertEquals(dtoResponsesSimulados.size(), resultado.getContent().size()); 
        assertEquals(dtoResponsesSimulados, resultado.getContent());
    }

    private ConsultaDenunciaDtoResponse criaConsultaDenunciaDtoResponse() {
        return new ConsultaDenunciaDtoResponse(1L, LocalDateTime.now().MIN, StatusDenuncia.RECEBIDA,
                LocalDateTime.now());
    }

    private Denuncia criarDenuncia() {
        return new Denuncia("Endereço", "Coordenadas", TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "Descrição");
    }

    private List<Denuncia> criarDenuncias() {
        List<Denuncia> denunciasList = new ArrayList();

        Denuncia denuncia1 = new Denuncia("Endereço 1", "Coordenadas", TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO,
                "Descrição");
        Denuncia denuncia2 = new Denuncia("Endereço 2", "Coordenadas", TipoDenuncia.QUEIMADA_URBANA, "Descrição");

        denunciasList.add(denuncia1);
        denunciasList.add(denuncia2);

        return denunciasList;
    }

    private CriarDenunciaDtoRequest criarCriarDenunciaDtoRequest() {
        return new CriarDenunciaDtoRequest("Endereço Valido", "Cordenadas Validas",
                TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "Descrição Válida");
    }

    private CriarDenunciaDtoRequest criarCriarDenunciaDtoInvalidoRequest() {
        return new CriarDenunciaDtoRequest("", "",
                TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "");
    }

    private DenunciaDtoResponse criarDenunciaDtoResponse() {
        return new DenunciaDtoResponse(1l, LocalDateTime.now(), "Endereço", "Coordenadas",
                TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "Descrição", StatusDenuncia.RECEBIDA);
    }

}

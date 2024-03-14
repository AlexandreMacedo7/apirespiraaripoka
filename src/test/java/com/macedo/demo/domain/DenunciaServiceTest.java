package com.macedo.demo.domain;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;
import com.macedo.demo.domain.util.TesteUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.macedo.demo.domain.builders.DenunciaBuilder.criarDenunciasPadrao;
import static com.macedo.demo.domain.builders.DenunciaBuilder.umaDenuncia;
import static com.macedo.demo.domain.builders.DenunciaDTORequestBuilder.*;
import static com.macedo.demo.domain.builders.DenunciaDTOResponseBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DenunciaServiceTest {

    @InjectMocks
    private DenunciaService denunciaService;

    @Mock
    private DenunciaRepository denunciaRepository;

    @Mock
    private DenunciaMapper denunciaMapper;

    @Test
    @DisplayName("Deve criar uma denuncia válida")
    public void criaDenuncia_ComDadosValidos_RetornaDto() {

        // Arrange
        CriarDenunciaDtoRequest dtoRequest = denunciaDtoRequestValido();
        Denuncia denuncia = umaDenuncia().umaDenunciaPadrao().build();
        DenunciaDetalhadaDtoResponse dtoResponse = denunciaDetalhadaDtoResponse();

        when(denunciaMapper.toEntity(dtoRequest)).thenReturn(denuncia);

        when(denunciaRepository.save(any(Denuncia.class))).thenReturn(denuncia);

        when(denunciaMapper.toDto(denuncia)).thenReturn(dtoResponse);

        // Act
        DenunciaDetalhadaDtoResponse dtoResponseReal = denunciaService.create(dtoRequest);

        // Assert
        assertThat(dtoResponse).isEqualTo(dtoResponseReal);
    }

    @Test//Necessita correção
    public void criaDenuncia_ComDadosInvalidos_RetornaExcecao() {

        // Arrange

        CriarDenunciaDtoRequest dtoInvalidoRequest = denunciaDtoRequestInvalido();

        when(denunciaMapper.toEntity(dtoInvalidoRequest)).thenThrow(RuntimeException.class);

        // Act & Assert

        assertThatThrownBy(() -> denunciaService.create(dtoInvalidoRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Deve consultar uma denuncia por id, e retornar um dto response")
    public void getDenunciaId_Existente_RetornaDto() {

        // Arrange
        Long id = 1L;
        Denuncia denuncia = umaDenuncia().umaDenunciaPadrao().build();
        ConsultaStatusDenunciaDtoResponse dtoResponse = consultaStatusDenunciaDtoResponse();

        when(denunciaRepository.findById(id)).thenReturn(Optional.of(denuncia));
        when(denunciaMapper.toDtoConsulta(denuncia)).thenReturn(dtoResponse);

        // Act
        ConsultaStatusDenunciaDtoResponse dtoResponseReal = denunciaService.getDenunciaById(id);

        // Assert

        verify(denunciaMapper).toDtoConsulta(denuncia);
        assertEquals(dtoResponse, dtoResponseReal);

    }

    @Test
    @DisplayName("Deve lançar uma exceção EntityNotFoundException em caso de Id inválido")
    public void getDenunciaId_Inexistente_RetornaExcecao() {

        // Arrange
        Long id = 1L;
        when(denunciaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> denunciaService.getDenunciaById(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("Deve retornar todas as denuncias")
    @Test
    public void getAllDenuncia_DeveRetornarPaginaDeDtoResponse() {
        // Arrange
        Pageable pageable = TesteUtil.criarPageable(0,20);
        List<Denuncia> denuncias = criarDenunciasPadrao();
        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denuncias, pageable);

        when(denunciaRepository.findAll(pageable)).thenReturn(paginaDenuncias);

        List<DenunciaDetalhadaDtoResponse> dtoResponsesSimulados = denuncias.stream()
                .map(denunciaMapper::toDto)
                .collect(Collectors.toList());

        // Act
        Page<DenunciaDetalhadaDtoResponse> resultado = denunciaService.getAllDenuncia(pageable);

        // Assert
        assertThat(resultado).isNotEmpty();
        assertEquals(dtoResponsesSimulados.size(), resultado.getContent().size());
        assertThat(resultado).hasSize(2);
        assertEquals(dtoResponsesSimulados, resultado.getContent());
    }

    @Test
    @DisplayName("Deve atualizar uma denuncia existente")
    public void updateDenuncia_DeveAtualizarStatusDenuncia_RetornarDtoResponse() {
        //Arrange

        AtualizarStatusDenunciaDtoRequest dtoRequest = novoStatusDenunciaDtoRequest();

        Denuncia denuncia = umaDenuncia().umaDenunciaPadrao().build();
        Long id = denuncia.getId();

        denuncia.atualizaStatusDenuncia(dtoRequest.statusDenuncia());

        DenunciaDetalhadaDtoResponse dtoResponse = denunciaDtoResponseComDenunciaAtualizada(denuncia);

        when(denunciaRepository.findById(id)).thenReturn(Optional.of(denuncia));
        when(denunciaRepository.save(any(Denuncia.class))).thenReturn(denuncia);
        when(denunciaMapper.toDto(denuncia)).thenReturn(dtoResponse);

        // Act
        DenunciaDetalhadaDtoResponse dtoResponseReal = denunciaService.updateDenuncia(id, dtoRequest);

        // Assert
        assertEquals(dtoResponse, dtoResponseReal); // O DTO de resposta deve corresponder ao esperado após a atualização;
        assertEquals(dtoRequest.statusDenuncia(), denuncia.getStatusDenuncia()); //O status da denúncia deve ser atualizado corretamente;

    }
    @Test
    @DisplayName("Deve trazer todas as denuncias de um determinado periodo")
    public void getDenunciasPorPeriodo_DeveRetornarDenunciasDtoResponse(){

        //Arrange
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        Pageable pageable = TesteUtil.criarPageable(0,10);
        List<Denuncia> denuncias = criarDenunciasPadrao();
        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denuncias, pageable);

        when(denunciaRepository.findByDataDenunciaBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), pageable))
                .thenReturn(paginaDenuncias);

        //Act
        Page<DenunciaDetalhadaDtoResponse> result = denunciaService.getDenunciasPorPeriodo(startDate, endDate,pageable);

        //Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(denuncias.size());
    }
}

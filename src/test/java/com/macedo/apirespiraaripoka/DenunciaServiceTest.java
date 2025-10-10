package com.macedo.apirespiraaripoka;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;
import com.macedo.apirespiraaripoka.util.TesteUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.never;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.macedo.apirespiraaripoka.builders.DenunciaBuilder.criarDenunciasPadrao;
import static com.macedo.apirespiraaripoka.builders.DenunciaBuilder.umaDenuncia;
import static com.macedo.apirespiraaripoka.builders.DenunciaDTORequestBuilder.denunciaDtoRequestValido;
import static com.macedo.apirespiraaripoka.builders.DenunciaDTORequestBuilder.novoStatusDenunciaDtoRequest;
import static com.macedo.apirespiraaripoka.builders.DenunciaDTOResponseBuilder.*;
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
        verify(denunciaMapper).toEntity(dtoRequest);
        verify(denunciaRepository).save(any(Denuncia.class));
        verify(denunciaMapper).toDto(denuncia);
    }

    @Test
    @DisplayName("Deve consultar uma denuncia por id")
    public void getDenunciaId_Existente_RetornaDto() {
        Long id = 1L;
        Denuncia denuncia = umaDenuncia().umaDenunciaPadrao().build();
        ConsultaStatusDenunciaDtoResponse dtoResponse = consultaStatusDenunciaDtoResponse();

        when(denunciaRepository.findById(id)).thenReturn(Optional.of(denuncia));
        when(denunciaMapper.toDtoConsulta(denuncia)).thenReturn(dtoResponse);

        ConsultaStatusDenunciaDtoResponse resultado = denunciaService.getDenunciaById(id);

        assertThat(resultado)
            .isNotNull()
            .isEqualTo(dtoResponse);
        verify(denunciaMapper).toDtoConsulta(denuncia);
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
        Pageable pageable = TesteUtil.criarPageable(0, 20);
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
        assertThat(resultado).hasSize(5);
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
    public void getDenunciasPorPeriodo_DeveRetornarDenunciasDtoResponse() {

        //Arrange
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        Pageable pageable = TesteUtil.criarPageable(0, 10);
        List<Denuncia> denuncias = criarDenunciasPadrao();
        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denuncias, pageable);

        when(denunciaRepository.findByDataDenunciaBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), pageable))
                .thenReturn(paginaDenuncias);

        //Act
        Page<DenunciaDetalhadaDtoResponse> result = denunciaService.getDenunciasPorPeriodo(startDate, endDate, pageable);

        //Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(denuncias.size());
    }

    @Test
    @DisplayName("Deve trazer todas as denuncias de um determinado tipo")
    public void getDenunciasPorTipo_DeveRetornarDenunciasDtoResponse() {

        //Arrange

        var tipo = TipoDenuncia.DESMATAMENTO_RURAL;

        Pageable pageable = TesteUtil.criarPageable(0, 10);
        List<Denuncia> denuncias = criarDenunciasPadrao();

        List<Denuncia> denunciasTipo = denuncias.stream()
                .filter(denuncia -> denuncia.getTipoDenuncia() == tipo)
                .collect(Collectors.toList());

        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denunciasTipo, pageable);

        when(denunciaRepository.findByTipoDenuncia(tipo, pageable)).thenReturn(paginaDenuncias);

        //Act
        Page<DenunciaDetalhadaDtoResponse> result = denunciaService.getDenunciasPorTipo(tipo, pageable);

        //Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Deve trazer todas as denuncias de um determinado periodo e tipo")
    public void getDenunciasPorPeriodoETipo_DeveRetornarDenunciasDtoResponse() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        TipoDenuncia tipo = TipoDenuncia.DESMATAMENTO_RURAL;
        Pageable pageable = TesteUtil.criarPageable(0, 10);

        List<Denuncia> denuncias = criarDenunciasPadrao();
        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denuncias, pageable);

        when(denunciaRepository.findByDataDenunciaBetweenAndTipoDenuncia(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX),
                tipo,
                pageable
        )).thenReturn(paginaDenuncias);

        Page<DenunciaDetalhadaDtoResponse> resultado =
                denunciaService.getDenunciasPorPeriodoETipo(startDate, endDate, tipo, pageable);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.getContent()).hasSize(5);
    }

    @Test
    @DisplayName("Deve trazer todas as denuncias de um status")
    public void getDenunciasPorStatus_DeveRetornarDenunciasDtoResponse() {

        //Arrange

        var status = StatusDenuncia.RECEBIDA;

        Pageable pageable = TesteUtil.criarPageable(0, 10);
        List<Denuncia> denuncias = criarDenunciasPadrao();

        List<Denuncia> denunciasTipo = denuncias.stream()
                .filter(denuncia -> denuncia.getStatusDenuncia() == status)
                .collect(Collectors.toList());

        Page<Denuncia> paginaDenuncias = TesteUtil.criarPagina(denunciasTipo, pageable);

        when(denunciaRepository.findByStatus(status, pageable)).thenReturn(paginaDenuncias);

        //Act
        Page<DenunciaDetalhadaDtoResponse> result = denunciaService.getDenunciaStatus(status, pageable);

        //Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(5);
    }

    @Test
    @DisplayName("Deve retornar total de denúncias")
    public void getTotalDenuncias_DeveRetornarQuantidadeCorreta() {
        when(denunciaRepository.count()).thenReturn(5L);
        long total = denunciaService.getTotalDenuncias();
        assertThat(total).isEqualTo(5L);
    }

    @Test
    @DisplayName("Deve retornar total de denúncias por tipo")
    public void getTotalDenunciasPorTipo_DeveRetornarMapaCorreto() {
        List<Denuncia> denuncias = criarDenunciasPadrao();
        when(denunciaRepository.findAll()).thenReturn(denuncias);

        Map<TipoDenuncia, Long> resultado = denunciaService.getTotalDenunciasPorTipo();

        assertThat(resultado)
                .containsKey(TipoDenuncia.DESMATAMENTO_RURAL)
                .containsValues(1L);
    }
    @Test
    @DisplayName("Deve retornar total de denúncias por status")
    public void getTotalDenunciasPorStatus_DeveRetornarMapaCorreto() {
        List<Denuncia> denuncias = criarDenunciasPadrao();
        when(denunciaRepository.findAll()).thenReturn(denuncias);

        Map<StatusDenuncia, Long> resultado = denunciaService.getTotalDenunciasPorStatus();

        assertThat(resultado)
                .containsKey(StatusDenuncia.RECEBIDA)
                .containsValues(5L);
    }

    @Test
    @DisplayName("Deve falhar ao buscar denúncias com datas inválidas")
    public void getDenunciasPorPeriodo_DataFinalMenorQueInicial_DeveLancarExcecao() {
        // Arrange
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.plusDays(1);
        Pageable pageable = TesteUtil.criarPageable(0, 10);

        // Act & Assert
        assertThatThrownBy(() ->
            denunciaService.getDenunciasPorPeriodo(startDate, endDate, pageable))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Data inicial não pode ser posterior à data final");
    }

    @Test
    @DisplayName("Deve falhar ao atualizar status com valor nulo")
    public void updateDenuncia_StatusNulo_DeveLancarExcecao() {
        // Arrange
        Long id = 1L;
        AtualizarStatusDenunciaDtoRequest request = new AtualizarStatusDenunciaDtoRequest(null);

        // Act & Assert
        assertThatThrownBy(() -> denunciaService.updateDenuncia(id, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Status da denúncia não pode ser nulo");

        verify(denunciaRepository, never()).findById(any());
        verify(denunciaRepository, never()).save(any());
    }

}

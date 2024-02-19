package com.macedo.demo.domain;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.entity.dto.AtualizarStatusDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.ConsultaStatusDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.entity.dto.CriarDenunciaDtoRequest;
import com.macedo.apirespiraaripoka.entity.dto.DenunciaDetalhadaDtoResponse;
import com.macedo.apirespiraaripoka.repository.DenunciaRepository;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;
import com.macedo.apirespiraaripoka.util.mapper.DenunciaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //Necessica melhora
    @Test
    public void getAllDenuncia_DeveRetornarPaginaDeDtoResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        List<Denuncia> denuncias = criarDenuncias();
        Page<Denuncia> paginaDenuncias = new PageImpl<>(denuncias, pageable,
                denuncias.size());

        when(denunciaRepository.findAll(pageable)).thenReturn(paginaDenuncias);

        List<DenunciaDetalhadaDtoResponse> dtoResponsesSimulados = denuncias.stream()
                .map(denunciaMapper::toDto)
                .collect(Collectors.toList());

        // Act
        Page<DenunciaDetalhadaDtoResponse> resultado = denunciaService.getAllDenuncia(pageable);

        // Assert
        assertEquals(dtoResponsesSimulados.size(), resultado.getContent().size());
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


    private List<Denuncia> criarDenuncias() {
        List<Denuncia> denunciasList = new ArrayList();

        Denuncia denuncia1 = new Denuncia("Endereço 1", "Coordenadas", TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO,
                "Descrição");
        Denuncia denuncia2 = new Denuncia("Endereço 2", "Coordenadas", TipoDenuncia.QUEIMADA_URBANA, "Descrição");

        denunciasList.add(denuncia1);
        denunciasList.add(denuncia2);

        return denunciasList;
    }
}

package com.macedo.demo.domain;

import static com.macedo.demo.domain.builders.DenunciaBuilder.umaDenuncia;
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

import static com.macedo.demo.domain.builders.DenunciaDTORequestBuilder.denunciaDtoRequestValido;

import static com.macedo.demo.domain.builders.DenunciaDTOResponseBuilder.denunciaDetalhadaDtoResponse;

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
        ConsultaStatusDenunciaDtoResponse dtoResponseSimulado = criaConsultaDenunciaDtoResponse();

        when(denunciaRepository.findById(1l)).thenReturn(Optional.of(denuncia));
        when(denunciaMapper.toDtoConsulta(denuncia)).thenReturn(dtoResponseSimulado);

        // Act
        ConsultaStatusDenunciaDtoResponse dtoResponseReal = denunciaService.getDenunciaById(id);

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
    public void updateDenuncia_DeveAtualizarStatusDenuncia_RetornarDtoResponse(){
        //Arrange
       
        StatusDenuncia novoStatus = StatusDenuncia.ANALISANDO;
        AtualizarStatusDenunciaDtoRequest dtoRequest = new AtualizarStatusDenunciaDtoRequest(novoStatus);

        Denuncia denunciaExistente = criarDenuncia();
        Long id = denunciaExistente.getId();
        Denuncia denunciaAtualizada = denunciaExistente;
        denunciaAtualizada.atualizaStatusDenuncia(novoStatus);

        DenunciaDetalhadaDtoResponse dtoResponseSimulado = criarDenunciaDtoResponseComDenunciaAtualizada(denunciaAtualizada);
        
        when(denunciaRepository.findById(id)).thenReturn(Optional.of(denunciaExistente));
        when(denunciaRepository.save(any(Denuncia.class))).thenReturn(denunciaAtualizada);
        when(denunciaMapper.toDto(denunciaAtualizada)).thenReturn(dtoResponseSimulado);

        // Act
        DenunciaDetalhadaDtoResponse resultadoDto = denunciaService.updateDenuncia(id, dtoRequest);

        // Assert
        assertEquals(dtoResponseSimulado, resultadoDto);
        assertEquals(novoStatus, denunciaExistente.getStatusDenuncia()); // Verifica se o status foi atualizado corretamente

    }


    private ConsultaStatusDenunciaDtoResponse criaConsultaDenunciaDtoResponse() {
        return new ConsultaStatusDenunciaDtoResponse(1L, LocalDateTime.now().MIN, StatusDenuncia.RECEBIDA,
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

    private DenunciaDetalhadaDtoResponse criarDenunciaDtoResponseComDenunciaAtualizada(Denuncia denunciaAtualizada) {
       return new DenunciaDetalhadaDtoResponse(denunciaAtualizada.getId(), denunciaAtualizada.getDateTime(),
       denunciaAtualizada.getEndereco(),denunciaAtualizada.getCoordenadasGeograficas(), denunciaAtualizada.getTipoDenuncia(),
        denunciaAtualizada.getDescricao(), denunciaAtualizada.getStatusDenuncia());
    }

    private CriarDenunciaDtoRequest criarCriarDenunciaDtoInvalidoRequest() {
        return new CriarDenunciaDtoRequest("", "",
                TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "");
    }

}

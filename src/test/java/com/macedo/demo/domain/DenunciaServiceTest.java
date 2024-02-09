package com.macedo.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.macedo.apirespiraaripoka.entity.Denuncia;
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
        DenunciaDtoResponse dtoResponse = criarDenunciaDtoResponse();

        when(denunciaMapper.toEntity(dtoRequest)).thenReturn(denunciaSalva);

        when(denunciaRepository.save(any(Denuncia.class))).thenReturn(denunciaSalva);

        when(denunciaMapper.toDto(denunciaSalva)).thenReturn(dtoResponse);

        // Act
        DenunciaDtoResponse atualDtoResponse = denunciaService.create(dtoRequest);

        // Assert
        assertThat(dtoResponse).isEqualTo(atualDtoResponse);
    }

    @Test
    public void criaDenuncia_ComDadosInvalidos_RetornaExcecao() {

        // Arrange

        CriarDenunciaDtoRequest dtoInvalidoRequest = criarCriarDenunciaDtoInvalidoRequest();

        when(denunciaMapper.toEntity(dtoInvalidoRequest)).thenThrow(RuntimeException.class);

        // Act & Assert

        assertThatThrownBy(() -> denunciaService.create(dtoInvalidoRequest)).isInstanceOf(RuntimeException.class);
    }

    private Denuncia criarDenuncia() {
        return new Denuncia("Endereço", "Coordenadas", TipoDenuncia.QUEIMADA_DE_LIXO_DOMESTICO, "Descrição");
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

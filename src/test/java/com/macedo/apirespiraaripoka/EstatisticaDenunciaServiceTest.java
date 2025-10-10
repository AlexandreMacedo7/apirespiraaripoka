package com.macedo.apirespiraaripoka;

import com.macedo.apirespiraaripoka.entity.dto.EstatisticaDenunciaDtoResponse;
import com.macedo.apirespiraaripoka.service.DenunciaService;
import com.macedo.apirespiraaripoka.service.EstatisticaDenunciaService;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstatisticaDenunciaServiceTest {

    @InjectMocks
    private EstatisticaDenunciaService estatisticaService;

    @Mock
    private DenunciaService denunciaService;

    @Test
    @DisplayName("Deve calcular estatísticas das denúncias corretamente")
    public void calcularEstatisticasDenuncias_DeveRetornarEstatisticasCorretas() {
        // Arrange
        long totalDenuncias = 10L;
        Map<TipoDenuncia, Long> totalPorTipo = Map.of(
                TipoDenuncia.DESMATAMENTO_RURAL, 6L,
                TipoDenuncia.QUEIMADA_RURAL, 4L
        );
        Map<StatusDenuncia, Long> totalPorStatus = Map.of(
                StatusDenuncia.RECEBIDA, 7L,
                StatusDenuncia.ANALISANDO, 3L
        );

        when(denunciaService.getTotalDenuncias()).thenReturn(totalDenuncias);
        when(denunciaService.getTotalDenunciasPorTipo()).thenReturn(totalPorTipo);
        when(denunciaService.getTotalDenunciasPorStatus()).thenReturn(totalPorStatus);

        // Act
        EstatisticaDenunciaDtoResponse resultado = estatisticaService.calcularEstatisticasDenuncias();

        // Assert
        assertThat(resultado.totalDenuncias()).isEqualTo(totalDenuncias);
        assertThat(resultado.totalPorTipo())
                .hasSize(2)
                .containsEntry(TipoDenuncia.DESMATAMENTO_RURAL.name(), 6L)
                .containsEntry(TipoDenuncia.QUEIMADA_RURAL.name(), 4L);
        assertThat(resultado.totalPorStatus())
                .hasSize(2)
                .containsEntry(StatusDenuncia.RECEBIDA.name(), 7L)
                .containsEntry(StatusDenuncia.ANALISANDO.name(), 3L);
    }
}

package com.macedo.apirespiraaripoka.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macedo.apirespiraaripoka.entity.Denuncia;
import com.macedo.apirespiraaripoka.util.enums.StatusDenuncia;
import com.macedo.apirespiraaripoka.util.enums.TipoDenuncia;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    Page<Denuncia> findByDataDenunciaBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<Denuncia> findByTipoDenuncia(TipoDenuncia tipoDenuncia, Pageable pageable);

    Page<Denuncia> findByDataDenunciaBetweenAndTipoDenuncia(LocalDateTime atStartOfDay, LocalDateTime atTime,
                                                            TipoDenuncia tipoDenuncia, Pageable pageable);

    Page<Denuncia> findByStatus(StatusDenuncia status, Pageable pageable);
}
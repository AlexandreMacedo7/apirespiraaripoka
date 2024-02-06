package com.macedo.apirespiraaripoka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macedo.apirespiraaripoka.entity.Denuncia;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
}

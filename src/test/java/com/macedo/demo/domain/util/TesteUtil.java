package com.macedo.demo.domain.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TesteUtil {

    public static Pageable criarPageable(int pagina, int tamanhoPagina){
        return PageRequest.of(pagina, tamanhoPagina);
    }
    public static <T>Page<T> criarPagina(List<T> conteudo, Pageable pageable){
        return new PageImpl<>(conteudo, pageable, conteudo.size());
    }
}

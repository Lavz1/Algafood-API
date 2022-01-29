package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> todos();
    Estado porID(Long id);
    Estado salvar(Estado estado);
    void remover(Long id);
}

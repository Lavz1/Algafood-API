package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> todos();
    Restaurante porID(Long id);
    Restaurante salvar(Restaurante Restaurante);
    void remover(Long id);
}

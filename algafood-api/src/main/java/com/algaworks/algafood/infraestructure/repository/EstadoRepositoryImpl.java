package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Estado> todos() {
        TypedQuery<Estado> query = entityManager.createQuery("from Estado", Estado.class);
        return query.getResultList();
    }

    @Override
    public Estado porID(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado adicionar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Estado estado = porID(id);
        entityManager.remove(estado);
    }
}

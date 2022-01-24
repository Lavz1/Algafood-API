package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Permissao> todas() {
        TypedQuery<Permissao> query = entityManager.createQuery("from Permissao", Permissao.class);
        return query.getResultList();
    }

    @Override
    public Permissao porID(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Transactional
    @Override
    public Permissao adicionar(Permissao permissao) {
        return entityManager.merge(permissao);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Permissao permissao = porID(id);
        entityManager.remove(permissao);
    }
}
 
package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<FormaPagamento> todas() {
        TypedQuery<FormaPagamento> query = entityManager.createQuery("from FormaPagamento", FormaPagamento.class);
        return query.getResultList();
    }

    @Override
    public FormaPagamento porID(Long id) {
        return entityManager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento adicionar(FormaPagamento formaPagamento) {
        return entityManager.merge(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        FormaPagamento formaPagamento = porID(id);
        entityManager.remove(formaPagamento);
    }
}

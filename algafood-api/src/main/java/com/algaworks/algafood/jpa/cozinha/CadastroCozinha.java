package com.algaworks.algafood.jpa.cozinha;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager entityManager;

//    public List<Cozinha> listar(){
//        TypedQuery<Cozinha> query = entityManager.createQuery("from Cozinha", Cozinha.class);
//        return query.getResultList();
//    }
//
//    public Cozinha buscar(Long id){
//        return entityManager.find(Cozinha.class, id);
//    }

//    @Transactional
//    public void update(Long id){
//        Cozinha cozinha = entityManager.find(Cozinha.class, id);
//        cozinha.setNome("Tailandesa FROM");
//
//        entityManager.merge(cozinha);
//    }


//    @Transactional
//    public Cozinha adicionar(Cozinha cozinha){
//        return entityManager.merge(cozinha);
//    }

//    @Transactional
//    public void excluir(Long id){
//        Cozinha cozinha = buscar(id);
//        entityManager.remove(cozinha);
//    }

}

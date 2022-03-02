package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
//        String jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial" +
//                "and :taxaFinal";

        StringBuilder jpql = new StringBuilder("from Restaurante where 0 = 0 ");
        Map<String, Object> parametros = new HashMap<>();

        if (StringUtils.hasLength(nome)) {
            jpql.append(" and nome like :nome");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaInicial != null) {
            jpql.append(" and taxaFrete >= :taxaInicial");
            parametros.put("taxaInicial", taxaInicial);
        }

        if (taxaFinal != null) {
            jpql.append(" and taxaFrete <= :taxaFinal");
            parametros.put("taxaFinal", taxaFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((key, value) -> query.setParameter(key, value));
//        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//        }

        return query.getResultList();
    }

}

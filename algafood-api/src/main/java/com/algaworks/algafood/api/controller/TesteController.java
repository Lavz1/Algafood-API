package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/primeira")
    public Optional<Cozinha> cozinhaPrimeira() {
        return cozinhaRepository.buscarPrimeiro();
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> taxaFreteEntreValores(@RequestParam BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }


//    @GetMapping("/restaurantes/find-impl")
//    public List<Restaurante> find(@RequestParam String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
//        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
//    }


    /* @GetMapping("/restaurantes/por-nome-cozinhaId")
     public List<Restaurante> findByNomeContaining(@RequestParam String nome, Long cozinhaId) {
         return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
     }*/
    @GetMapping("/restaurantes/por-nome-cozinhaId")
    public List<Restaurante> findByNomeContaining(@RequestParam String nome, Long cozinhaId) {
        return restauranteRepository.procurarPorNome(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-nome-one")
    public ResponseEntity<Restaurante> findFirstRestauranteByNomeContaining(@RequestParam String nome) {
        Optional<Restaurante> restaurante = restauranteRepository.findFirstRestauranteByNomeContaining(nome);

        if (restaurante.isPresent()) return ResponseEntity.ok(restaurante.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/restaurantes/restaurantes-por-cozinha")
    public int countRestaurantsByCozinhaId(@RequestParam Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/restaurantes/find-impl")
    public List<Restaurante> restaurantesPorNomeFrete(@RequestParam String nome,
                                                      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }


    /*@GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().
                and(comNomeSemelhante(nome)));
    }*/

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findComFreteGratis(nome);
    }

//    @GetMapping("/restaurantes/com-frete-gratis")
//    public List<Restaurante> restaurantesComFreteGratis(String nome) {
//        return restauranteRepository.findComFreteGratis(nome);
//    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping()
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }


    @GetMapping("/{restaurante_id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restaurante_id){
        Optional<Restaurante> restaurante= restauranteRepository.findById(restaurante_id);

        if(restaurante.isPresent()){
            return  ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try {
            restaurante = cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{restaurante_id}")
    public ResponseEntity<?> atualizar(@PathVariable Long restaurante_id,
                                                 @RequestBody  Restaurante restaurante){
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restaurante_id);

            if(restauranteAtual.isEmpty()) return ResponseEntity.notFound().build();

            BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
            Restaurante restauranteSalva = cadastroRestaurante.salvar(restauranteAtual.get());

            return ResponseEntity.ok(restauranteSalva);

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // UNICO PATCH DO SISTEMA APENAS COMO EXEMPLO.
    @PatchMapping("/{restaurante_id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restaurante_id, @RequestBody Map<String, Object> campos){

        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restaurante_id);
        if(restauranteAtual.isEmpty()) return ResponseEntity.notFound().build();

        merge(campos, restauranteAtual.get());

        return atualizar(restaurante_id, restauranteAtual.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        System.out.println("restaurante origem: " + restauranteOrigem);
        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

//            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

//    @DeleteMapping("/{restaurante_id")
//    public ResponseEntity<?> remover(@PathVariable Long restaurante_id){
//        try {
//            cadastroCidade.remover(cidade_id);
//            return ResponseEntity.noContent().build();
//
//        } catch (EntidadeNaoEncontradaException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//
//        } catch (EntidadeEmUsoException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//    }
}



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
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping()
    public List<Restaurante> listar(){
        return restauranteRepository.todos();
    }


    @GetMapping("/{restaurante_id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restaurante_id){
        Restaurante restaurante= restauranteRepository.porID(restaurante_id);

        if(restaurante != null){
            return  ResponseEntity.ok(restaurante);
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
            Restaurante restauranteAtual = restauranteRepository.porID(restaurante_id);

            if(restauranteAtual == null) return ResponseEntity.notFound().build();

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
            restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);

            return ResponseEntity.ok(restauranteAtual);

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // UNICO PATCH DO SISTEMA APENAS COMO EXEMPLO.
    @PatchMapping("/{restaurante_id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restaurante_id, @RequestBody Map<String, Object> campos){

        Restaurante restauranteAtual = restauranteRepository.porID(restaurante_id);
        if(restauranteAtual == null) return ResponseEntity.notFound().build();

        merge(campos, restauranteAtual);

        return atualizar(restaurante_id, restauranteAtual);
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
}



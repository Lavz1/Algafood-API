package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                                 @RequestBody  Restaurante restauranteParam){
        try {
            Restaurante restaurante = restauranteRepository.porID(restaurante_id);

            if(restaurante == null) return ResponseEntity.notFound().build();

            BeanUtils.copyProperties(restauranteParam, restaurante, "id");
            restaurante = cadastroRestaurante.salvar(restaurante);

            return ResponseEntity.ok(restaurante);

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}

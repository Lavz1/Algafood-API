package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping()
    public List<Cozinha> listar(){
        return cozinhaRepository.todas();
    }


    @GetMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinha_id){
        Cozinha cozinha = cozinhaRepository.porID(cozinha_id);

        if(cozinha != null){
            return  ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinha_id, @RequestBody Cozinha cozinhaParam){
        Cozinha cozinha = cozinhaRepository.porID(cozinha_id);

        if(cozinha == null)  return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cozinhaParam, cozinha, "id");
        cozinha = cadastroCozinha.salvar(cozinha);
        return ResponseEntity.ok(cozinha);
    }

    @DeleteMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinha_id){
        try {
            cadastroCozinha.remover(cozinha_id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping()
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }


    @GetMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinha_id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinha_id);

        //cozinha != null
        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        }

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinha_id, @RequestBody Cozinha cozinhaParam) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinha_id);

        if (cozinha.isEmpty()) return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cozinhaParam, cozinha.get(), "id");
        Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinha.get());
        return ResponseEntity.ok(cozinhaSalva);
    }

    @DeleteMapping("/{cozinha_id}")
    public ResponseEntity<?> remover(@PathVariable Long cozinha_id) {
        try {
            cadastroCozinha.remover(cozinha_id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((e.getMessage()));
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

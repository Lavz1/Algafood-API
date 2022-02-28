package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping()
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }


    @GetMapping("/{cidade_id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidade_id){
        Optional<Cidade> cidade = cidadeRepository.findById(cidade_id);

        if(cidade.isPresent()){
            return  ResponseEntity.ok(cidade.get());
        }

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Cidade adicionar(@RequestBody Cidade cidade){
        return cadastroCidade.salvar(cidade);
    }

    @PutMapping("/{cidade_id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long cidade_id, @RequestBody Cidade cidadeParam){
        Optional<Cidade> cidade = cidadeRepository.findById(cidade_id);

        if(cidade.isEmpty())  return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cidadeParam, cidade.get(), "id");
        Cidade cidadeSalva = cadastroCidade.salvar(cidade.get());
        return ResponseEntity.ok(cidadeSalva);
    }

    @DeleteMapping("/{cidade_id}")
    public ResponseEntity<?> remover(@PathVariable Long cidade_id){
        try {
            cadastroCidade.remover(cidade_id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

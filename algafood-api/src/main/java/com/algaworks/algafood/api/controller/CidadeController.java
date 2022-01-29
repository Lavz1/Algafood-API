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

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping()
    public List<Cidade> listar(){
        return cidadeRepository.todas();
    }


    @GetMapping("/{cidade_id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidade_id){
        Cidade cidade = cidadeRepository.porID(cidade_id);

        if(cidade != null){
            return  ResponseEntity.ok(cidade);
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
        Cidade cidade = cidadeRepository.porID(cidade_id);

        if(cidade == null)  return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cidadeParam, cidade, "id");
        cidade = cadastroCidade.salvar(cidade);
        return ResponseEntity.ok(cidade);
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

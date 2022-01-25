package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping()
    public List<Cozinha> listar(){
        return cozinhaRepository.todas();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml(){
        return new CozinhasXmlWrapper(cozinhaRepository.todas());
    }

    @GetMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinha_id){
        Cozinha cozinha = cozinhaRepository.porID(cozinha_id);

        if(cozinha != null){
            return  ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.notFound().build();

//        return ResponseEntity.status(HttpStatus.OK).body(cozinha);


        /*HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .headers(httpHeaders)
                .build();*/
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaRepository.adicionar(cozinha);
    }

    @PutMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinha_id, @RequestBody Cozinha cozinhaParam){
        Cozinha cozinha = cozinhaRepository.porID(cozinha_id);

        if(cozinha == null)  return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cozinhaParam, cozinha, "id");
        cozinha = cozinhaRepository.adicionar(cozinha);
        return ResponseEntity.ok(cozinha);
    }

    @DeleteMapping("/{cozinha_id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinha_id){
        try {
            Cozinha cozinha = cozinhaRepository.porID(cozinha_id);

            if (cozinha != null) {
                cozinhaRepository.remover(cozinha_id);
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

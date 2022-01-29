package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping()
    public List<Estado> listar(){
        return estadoRepository.todos();
    }


    @GetMapping("/{estado_id}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estado_id){
        Estado estado = estadoRepository.porID(estado_id);

        if(estado != null){
            return  ResponseEntity.ok(estado);
        }

        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{estado_id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long estado_id, @RequestBody Estado estadoParam){
        Estado estado = estadoRepository.porID(estado_id);

        if(estado == null)  return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(estadoParam, estado, "id");
        estado = cadastroEstado.salvar(estado);
        return ResponseEntity.ok(estado);
    }

    @DeleteMapping("/{estado_id}")
    public ResponseEntity<?> remover(@PathVariable Long estado_id){
        try {
            cadastroEstado.remover(estado_id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

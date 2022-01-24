package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.jpa.cozinha.CadastroCozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CozinhaController {

    @Autowired
    private CadastroCozinha cadastroCozinha;

//    @GetMapping("/cozinhas")
//    public List<Cozinha> listar(){
//        return cadastroCozinha.listar();
//    }
}

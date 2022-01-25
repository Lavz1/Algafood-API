package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JsonRootName("cozinhas")
@Data
public class CozinhasXmlWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("cozinha")
    @NonNull
    private List<Cozinha> cozinhas;
}

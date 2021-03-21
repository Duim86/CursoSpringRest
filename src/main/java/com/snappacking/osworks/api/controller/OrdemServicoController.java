package com.snappacking.osworks.api.controller;


import com.snappacking.osworks.api.model.OrdemServicoInput;
import com.snappacking.osworks.api.model.OrdemServicoModel;
import com.snappacking.osworks.domain.model.OrdemServico;
import com.snappacking.osworks.domain.repository.OrdemServicoRepository;
import com.snappacking.osworks.domain.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

  @Autowired
  private GestaoOrdemServicoService gestaoOrdemServicoService;

  @Autowired
  private OrdemServicoRepository ordemServicoRepository;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput){

    OrdemServico ordemServico = toEntity(ordemServicoInput);
    return toModel(gestaoOrdemServicoService.criar(ordemServico));
  }

  @GetMapping
  public List<OrdemServicoModel> listar (){
    return toCollectionModel(ordemServicoRepository.findAll());
  }


  @GetMapping("/{ordemServicoId}")
  public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
    Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

    if(ordemServico.isPresent()) {
      OrdemServicoModel ordemServicoModel = modelMapper.map(ordemServico.get(), OrdemServicoModel.class);
      return ResponseEntity.ok(ordemServicoModel);
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{ordemServicoId}/finalizacao")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void finalizar(@PathVariable Long ordemServicoId) {
    gestaoOrdemServicoService.finalizar(ordemServicoId);
  }

  private OrdemServicoModel toModel(OrdemServico ordemServico){
    return modelMapper.map(ordemServico, OrdemServicoModel.class);
  }

  private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
    return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
  }

  private OrdemServico toEntity(OrdemServicoInput ordemServicoInput){
    return modelMapper.map(ordemServicoInput, OrdemServico.class);
  }
}

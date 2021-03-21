package com.snappacking.osworks.domain.service;

import com.snappacking.osworks.api.exceptionhandler.EntidadeNaoEncontradaException;
import com.snappacking.osworks.api.model.Comentario;
import com.snappacking.osworks.domain.exception.NegocioException;
import com.snappacking.osworks.domain.model.Cliente;
import com.snappacking.osworks.domain.model.OrdemServico;
import com.snappacking.osworks.domain.model.StatusOrdemServico;
import com.snappacking.osworks.domain.repository.ClienteRepository;
import com.snappacking.osworks.domain.repository.ComentarioRepository;
import com.snappacking.osworks.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class GestaoOrdemServicoService {

  @Autowired
  private OrdemServicoRepository ordemServicoRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private ComentarioRepository comentarioRepository;

  public OrdemServico criar(OrdemServico ordemServico) {

    Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
            .orElseThrow(() -> new NegocioException("Cliente não encontrado"));

    ordemServico.setCliente(cliente);
    ordemServico.setStatus(StatusOrdemServico.ABERTA);
    ordemServico.setDataAbertura(OffsetDateTime.now());

    return ordemServicoRepository.save(ordemServico);
  }

  public void finalizar(Long ordemServicoId) {
    OrdemServico ordemServico = buscar(ordemServicoId);

    ordemServico.finalizar();
    ordemServicoRepository.save(ordemServico);
  }

  public Comentario adicionarComentario(Long ordemServicoId, String descricao){

    OrdemServico ordemServico = buscar(ordemServicoId);

    Comentario comentario = new Comentario();
    comentario.setDataEnvio(OffsetDateTime.now());
    comentario.setDescricao(descricao);
    comentario.setOrdemServico(ordemServico);

    return comentarioRepository.save(comentario);

  }

  private OrdemServico buscar(Long ordemServicoId) {
    return ordemServicoRepository.findById(ordemServicoId)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
  }
}

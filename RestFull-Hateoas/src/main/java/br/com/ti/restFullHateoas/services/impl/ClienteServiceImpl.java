package br.com.ti.restFullHateoas.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ti.restFullHateoas.entity.Cliente;
import br.com.ti.restFullHateoas.repositories.ClienteRepository;
import br.com.ti.restFullHateoas.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	Cliente registro = new Cliente();
	Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	public ClienteRepository enderecoRepository;

	@Override
	@Transactional
	public Cliente post(Cliente entity) {
		try {
			logger.debug("\tMétodo POST executado.");
			logger.debug("\tMétodo POST invocado");
			logger.debug(String.format("\tValor recebido: %s", entity.toString()));
			Cliente rg = enderecoRepository.save(entity);
			logger.info(String.format("\tValor persistido: %s", entity.toString()));
			return rg;
		} catch (Exception e) {
			logger.error(String.format("Error ao persistir registro. \nMensagem:%s", e.getMessage()));
			return null;
		}
	}

	@Override
	public Cliente get(Long id) {
		try {
			logger.debug("\tMétodo GET executado.");
			logger.debug("\tMétodo GET invocado");
			logger.debug(String.format("\tValor recebido: %s", id.toString()));
			registro = enderecoRepository.getOne(id);			
			logger.info(String.format("\tValor buscado: %s", registro.toString()));
			return registro;
		} catch (Exception e) {
			logger.error(String.format("Error ao buscar registro. \nMensagem:%s", e.getMessage()));
			return null;
		}
	}

	@Override
	@Transactional
	public void put(Cliente entity) {
		try {
			logger.debug("\tMétodo PUT executado.");
			logger.debug("\tMétodo PUT invocado");
			logger.debug(String.format("\tValor recebido: %s", entity.toString()));			
			enderecoRepository.save(entity);			
			logger.info(String.format("\tValor alterado: %s", entity.toString()));
		} catch (Exception e) {
			logger.error(String.format("Error ao alterar registro. \nMensagem:%s", e.getMessage()));
		}
	}

	@Override
	@Transactional
	public void delete(Cliente entity) {
		try {
			logger.debug("\tMétodo DELETE executado.");
			logger.debug("\tMétodo DELETE invocado");
			logger.debug(String.format("\tValor recebido: %s", entity.toString()));			
			enderecoRepository.delete(entity);			
			logger.info("\tValor entidade deletada:");
		} catch (Exception e) {
			logger.error(String.format("Error ao deletar. \nMensagem:%s", e.getMessage()));			
		}
	}

	@Override
	@Transactional
	public void patch(Cliente entity) {
		try {
			logger.debug("\tMétodo PATCH executado.");
			logger.debug("\tMétodo PATCH invocado");
			logger.debug(String.format("\tValor recebido: %s", entity.toString()));		
			enderecoRepository.save(entity);			
			logger.info(String.format("\tValor alterado: %s", entity.toString()));
		} catch (Exception e) {
			logger.error(String.format("Error ao atualizar. \nMensagem:%s", e.getMessage()));
		}
	}

	@Override
	@Transactional
	public List<Cliente> post(List<Cliente> entities) {
		try {
			List<Cliente> list = new ArrayList<Cliente>();
			logger.debug("\tMétodo POST LIST executado.");
			logger.debug("\tMétodo POST LIST invocado");
			logger.debug(String.format("\tValor recebido: %s", entities.toString()));			
			list =  enderecoRepository.saveAll(entities);
			logger.info(String.format("\tValor persistido: %s", entities.toString()));
			return list;
		} catch (Exception e) {
			logger.error(String.format("Error ao persistir a lista. \nMensagem:%s", e.getMessage()));
			return null;
		}
	}

	@Override
	@Transactional
	public void put(List<Cliente> entities) {
		try {			
			logger.debug("\tMétodo PUT LIST executado.");
			logger.debug("\tMétodo PUT LIST invocado");
			logger.debug(String.format("\tValor recebido: %s", entities.toString()));			
			enderecoRepository.saveAll(entities);
			logger.info(String.format("\tValor persistido: %s", entities.toString()));
		} catch (Exception e) {
			logger.error(String.format("Error ao atualizar a lista. \nMensagem:%s", e.getMessage()));
		}
	}

	@Override
	@Transactional
	public void delete(List<Cliente> entities) {
		try {
			logger.debug("\tMétodo DELETE LIST executado.");
			logger.debug("\tMétodo DELETE LIST invocado");
			logger.debug(String.format("\tValor recebido: %s", entities.toString()));			
			enderecoRepository.deleteAll(entities);
			logger.info("\tValor lista deletada ");
		} catch (Exception e) {
			logger.error(String.format("Error ao deletar a lista. \nMensagem:%s", e.getMessage()));
		}
	}

	@Override
	@Transactional
	public void patch(List<Cliente> entities) {
		try {
			logger.debug("\tMétodo PATCH LIST executado.");
			logger.debug("\tMétodo PATCH LIST invocado");
			logger.debug(String.format("\tValor recebido: %s", entities.toString()));			
			enderecoRepository.saveAll(entities);
			logger.info(String.format("\t lista alterada: %s",entities.toString()));
		} catch (Exception e) {
			logger.error(String.format("Error ao alterar a lista. \nMensagem:%s", e.getMessage()));
		}

	}

	@Override
	public List<Cliente> get() {
		try {
			List<Cliente> list = new ArrayList<Cliente>();
			logger.debug("\tMétodo GET LIST executado.");
			logger.debug("\tMétodo GET LIST invocado");			
			list = (enderecoRepository.findAll());
			logger.info(String.format("\t lista buscada: %s",list.toString()));
			return list;
		} catch (Exception e) {
			logger.error(String.format("Error ao get lista. \nMensagem:%s", e.getMessage()));
			return null;
		}
	}

}

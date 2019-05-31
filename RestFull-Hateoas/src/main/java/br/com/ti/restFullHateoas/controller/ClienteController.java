package br.com.ti.restFullHateoas.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ti.restFullHateoas.entity.Cliente;
import br.com.ti.restFullHateoas.interfaces.GenericOperationsController;
import br.com.ti.restFullHateoas.services.ClienteService;
import br.com.ti.restFullHateoas.services.clienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController implements GenericOperationsController<Cliente>{	
	
	Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	public ClienteService clienteService;	
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
			 				MediaType.APPLICATION_XML_VALUE},
				produces = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE,
							MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public Resource<Cliente> post(@RequestBody Cliente entity) {
		try {
			clienteService.post(entity);
			log.info("Registro inserido");			
			Link link = linkTo(Cliente.class).slash(entity.getId()).withSelfRel();
			Resource<Cliente> result = new Resource<Cliente>(entity,link);			
			return result;			
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método POST.\nMensagem: %s",e.getMessage()));
		}			
		return null;
	}
	
	@Override
	@PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void put(@RequestBody Cliente entity) {			
			try {
				clienteService.put(entity);
				log.info(String.format("Registro atualizado: %s",entity.toString()));
			} catch (Exception e) {
				log.error(String.format("Erro ao executar o método PUT.\nMensagem: %s",e.getMessage()));
			}
	}
	
	@Override
	@DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestBody Cliente entity) {
		try {
			clienteService.delete(entity);
			log.info(String.format("Registro(s) deletado(s): %s",entity.toString()));
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método DELETE.\nMensagem: %s",e.getMessage()));
		}		
	}

	@Override
	@GetMapping(value = "/{get}/",produces = {MediaType.APPLICATION_JSON_VALUE,
											  MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Resources<Cliente> get() {
		List<Cliente> allRegistros = new ArrayList<Cliente>();
		allRegistros.addAll(clienteService.get());
		List<Cliente> all = new ArrayList<Cliente>();
		try {
			for(Cliente registro : allRegistros) {
				String registroId = String.valueOf(registro.getIdCliente());
				Link selfLink = linkTo(Cliente.class).slash(registroId).withSelfRel();
				registro.add(selfLink);
				all.add(registro);
			}			
			Link link = linkTo(Cliente.class).withSelfRel();
			Resources<Cliente> result = new Resources<Cliente>(all,link);
			log.info(String.format("Registro(s) recuperados(s): %s",all.toString()));
			return result;
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método GET.\nMensagem: %s",e.getMessage()));
		}
		return null;
	}
	
	@Override
	@GetMapping(value = "/get/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,
											MediaType.APPLICATION_XML_VALUE,
											MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Resource<Cliente> get(@PathVariable Long id) {		
		try {
			Cliente registro = clienteService.get(id);			  
			Link link = linkTo(Cliente.class).slash(registro).withSelfRel();
			Resource<Cliente> result = new Resource<Cliente>(registro, link);
			log.info(String.format("Registro recuperado: %s",result.toString()));
			return result;
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método GET.\nMensagem: %s",e.getMessage()));
		}
		return null;
	}
	
	@Override
	@PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
							 MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void patch(@RequestBody Cliente entity) {		
		try {
			clienteService.patch(entity);
			log.info(String.format("Registro atualizado: %s",entity.toString()));
			Link link = linkTo(Cliente.class).slash(entity.getId()).withSelfRel();
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método PATCH.\nMensagem: %s",e.getMessage()));
		}		
	}
}

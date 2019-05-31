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

import br.com.ti.restFullHateoas.entity.Endereco;
import br.com.ti.restFullHateoas.entity.endereco;
import br.com.ti.restFullHateoas.interfaces.GenericOperationsController;
import br.com.ti.restFullHateoas.services.EnderecoService;
import br.com.ti.restFullHateoas.services.enderecoService;

//@RestController
//@RequestMapping("/endereco")
public class EnderecoController implements GenericOperationsController<Endereco>{
	
	
	Logger log = LoggerFactory.getLogger(EnderecoController.class);

	
	@Autowired
	public EnderecoService enderecoService;
	
	@PostMapping(value = "/{post}",consumes = {MediaType.APPLICATION_JSON_VALUE,
			 				MediaType.APPLICATION_XML_VALUE},
				produces = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE,
							MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public Resource<Endereco> post(@RequestBody Endereco entity) {
			try {
				enderecoService.post(entity);
				log.info("Registro inserido");
				Link link = linkTo(Endereco.class).slash(entity.getIdEndereco()).withSelfRel();
				Resource<Endereco> result = new Resource<Endereco>(entity,link);
				
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
	public void put(@RequestBody Endereco entity) {
			
			try {
				enderecoService.put(entity);
				log.info("Registro atualizado");
				Link link = linkTo(Endereco.class).slash(entity.getIdEndereco()).withSelfRel();
			} catch (Exception e) {
				log.error(String.format("Erro ao executar o método PUT.\nMensagem: %s",e.getMessage()));
			}
	}

	@Override
	@DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestBody Endereco entity) {
		try {
			enderecoService.delete(entity);
			log.info(String.format("Registro(s) deletado(s): %s",entity.toString()));
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método DELETE.\nMensagem: %s",e.getMessage()));
		}
		
	}


	@Override
	@GetMapping(value = "/{get}",produces = {MediaType.APPLICATION_JSON_VALUE,
							MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Resources<Endereco> get() {
		try {
			List<Endereco> allRegistros = enderecoService.get();
			log.info(String.format("Registro(s) recuperados(s): %s",allRegistros.toString()));
			List<Endereco> all = new ArrayList<Endereco>();
			for(Endereco registro : allRegistros) {
				String registroId = String.valueOf(registro.getIdEndereco());
				Link selfLink = linkTo(Endereco.class).slash(registroId).withSelfRel();
				registro.add(selfLink);
				all.add(registro);
			}
			
			Link link = linkTo(Endereco.class).withSelfRel();
			Resources<Endereco> result = new Resources<Endereco>(all,link);
			
			return result;
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método GET.\nMensagem: %s",e.getMessage()));
		}
		return null;
	}

	@Override
	@GetMapping(value = "/{registro}/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE},
				produces = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE,
							MediaTypes.HAL_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public Resource<Endereco> get(@PathVariable Long id) {
		
		try {
			Endereco registro = enderecoService.get(id);
			log.info(String.format("Registro recuperado: %s",registro.toString()));  
			Link link = linkTo(Endereco.class).slash(registro).withSelfRel();
			Resource<Endereco> result = new Resource<Endereco>(registro, link);
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
	public void patch(@RequestBody Endereco entity) {
		
		try {
			enderecoService.patch(entity);
			log.info(String.format("Registro atualizado: %s",entity.toString()));
			Link link = linkTo(Endereco.class).slash(entity.getIdEndereco()).withSelfRel();
		} catch (Exception e) {
			log.error(String.format("Erro ao executar o método PATCH.\nMensagem: %s",e.getMessage()));
		}
		
	}
}

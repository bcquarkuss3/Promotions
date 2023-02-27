package com.quarkus.bootcamp.nttdata.application;

import com.quarkus.bootcamp.nttdata.domain.entity.Promotion;
import com.quarkus.bootcamp.nttdata.domain.service.PromotionService;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/promotion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {

	@Inject
	PromotionService promotionService;
	
	// usando metricas de prometeus
	 private final MeterRegistry registry;
	 
	 
	 
	
	 
	 public PromotionResource(MeterRegistry registry) {
		 this.registry = registry;
	 }
	
	/**
	 * CREAR UNA PROMOCION
	 * */
	@POST
	public void create(Promotion promotion) {
		promotionService.set(promotion.getKey(), promotion);
	}

	/**
	 * OBTENER UNA PROMOCION POR EL CODIGO
	 * */
	@GET
	@Path("/{key}")
	public Response get(@PathParam("key") String key) {
		if("PROM123".equals(key)) {
			registry.counter("promotion.key", "type", "PROM123").increment();
		} else if("COCA".equals(key)){
			registry.counter("promotion.key", "type", "COCA").increment();
		} else {
			registry.counter("promotion.key", "type", "others").increment();
		}
		
		return Response.ok(promotionService.get(key)).status(201).build();
	}

	
	/**
	 * OBTENER TODAS LA PROMOCIONES
	 * */
	@GET
	public Response getAll() {
		return Response.ok(promotionService.getAll()).status(201).build();
	}

}

package com.quarkus.bootcamp.nttdata.domain.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.quarkus.bootcamp.nttdata.domain.entity.Promotion;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import jakarta.inject.Singleton;

@Singleton
public class PromotionService {

	private static final String MY_KEY = "promotions";
	HashCommands<String, String, Promotion> commands;

	public PromotionService(RedisDataSource ds) {
		commands = ds.hash(Promotion.class);
	}

	public void set(String field, Promotion value) {
		commands.hset(MY_KEY, field, value);
	}

	public Promotion get(String field) {
		return commands.hget(MY_KEY, field);
	}

	public List<Promotion> getAll() {
		Map<String, Promotion> lista = commands.hgetall(MY_KEY);
		return lista.values().stream().collect(Collectors.toList());
	}

}

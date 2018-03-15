package cz.coccinelles.gc.verificator.convert;

import org.springframework.core.convert.converter.Converter;

import cz.coccinelles.gc.verificator.model.Cache;

public class CacheToStringConverter implements Converter<Cache, String> {
	@Override
	public String convert(Cache cache) {
		return cache.getId();
	}
}

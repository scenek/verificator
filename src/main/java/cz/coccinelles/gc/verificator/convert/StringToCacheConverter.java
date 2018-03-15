package cz.coccinelles.gc.verificator.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cz.coccinelles.gc.verificator.dao.CacheDao;
import cz.coccinelles.gc.verificator.model.Cache;

@Component
public class StringToCacheConverter implements Converter<String, Cache> {

	@Autowired
	private CacheDao dao;
	
	@Override
	public Cache convert(String source) {
		return StringUtils.hasText(source) ? dao.get(source) : null;
	}
}
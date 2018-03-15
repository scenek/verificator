package cz.coccinelles.gc.verificator.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class StringToKeyConverter implements Converter<String, Key> {

	@Override
	public Key convert(String source) {
		return StringUtils.hasText(source) ? KeyFactory.stringToKey(source) : null;
	}

}

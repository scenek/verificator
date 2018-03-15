package cz.coccinelles.gc.verificator.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

public class CacheValidator {
	public boolean validate(Cache cache, BindingResult result) {
		if (!StringUtils.hasText(cache.getCode()))
			result.rejectValue("code", "required", "required");
		if (!StringUtils.hasText(cache.getTitle()))
			result.rejectValue("title", "required", "required");
		try {
			new URL(cache.getUrl());
		} catch (MalformedURLException e) {
			result.rejectValue("url", "invalidurl", e.getMessage());
		}

		return !result.hasErrors();
	}
}

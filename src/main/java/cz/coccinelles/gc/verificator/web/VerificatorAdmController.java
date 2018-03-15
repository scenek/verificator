package cz.coccinelles.gc.verificator.web;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cz.coccinelles.gc.verificator.dao.CacheDao;
import cz.coccinelles.gc.verificator.dao.StageDao;
import cz.coccinelles.gc.verificator.dao.UserStageDao;

public abstract class VerificatorAdmController {
	public static final String REDIR_PARAM = "redir";
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CacheDao cacheDao;

	@Autowired
	protected StageDao stageDao;
	
	@Autowired
	protected UserStageDao userStageDao;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields(new String[] { "stages" });
		// Nasledujici kvuli classloadingu v appengine
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(
				false));
		dataBinder.registerCustomEditor(Long.class, new CustomNumberEditor(
				Long.class, true));
		dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, true));
		dataBinder.registerCustomEditor(Float.class, new CustomNumberEditor(
				Float.class, NumberFormat.getInstance(new Locale("cs")), true));
		dataBinder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(
				true));
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat(), true));
		//dataBinder.registerCustomEditor(Key.class, new KeyEditor());
	}
}

package cz.coccinelles.gc.verificator.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.coccinelles.gc.verificator.dao.UserStageValidator;
import cz.coccinelles.gc.verificator.model.UserStage;
import cz.coccinelles.gc.verificator.model.ValidatorMessage;

@Controller
public class VerificatorController extends VerificatorAdmController {
	public static final String URL = "/verificator";
	private static final String FORM = "verificator";
	private static final String MODEL = "verificator";
	private static final String CAPTCHA = "ValidCaptcha";
	private static final String MSG = "message";
	//aaa

	@Autowired
	UserStageValidator validator;

	// http://wheelersoftware.com/articles/recaptcha-java-2.html

	@RequestMapping(URL)
	public String list(HttpServletRequest req, Model model) {
		/* TODO: kontrolovat čas posledního pokusu */
		/* TODO: kontrolovat denní počet nesprávných pokusů */
		//HttpSession session = req.getSession(true);
		UserStage stage = new UserStage();
		model.addAttribute(MODEL, stage);
		return "verificator";
	}

	@RequestMapping(value = URL, method = RequestMethod.POST)
	public String verify(HttpServletRequest req,
			@RequestParam("recaptcha_challenge_field") String challenge,
			@RequestParam("recaptcha_response_field") String response,
			@ModelAttribute(MODEL) UserStage stage, BindingResult result,
			Model model) {

		/*
		 * Převod čísla http://static.springsource.org/spring/docs/3.0.x/spring
		 * -framework-reference/html/validation.html#validation-conversion
		 */

		// Validate the reCAPTCHA
		/*
		 * String localAddr = req.getLocalAddr() == null ? "gae" : req
		 * .getLocalAddr(); if (!localAddr.equals("127.0.0.1")) {
		 */
		String remoteAddr = req.getRemoteAddr();
		// Get session and verify captcha
		HttpSession session = req.getSession();
		Boolean validCaptcha = false;
		if (!session.isNew()) {
			try {
				log.error(session.getAttribute(CAPTCHA).toString());
				validCaptcha = (Boolean) session.getAttribute(CAPTCHA);
			} catch (NullPointerException e) {
				validCaptcha = false;
			}
		}
		// First visit or captcha wasn't right in history
		if (!validCaptcha) {
			ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
			reCaptcha.setPrivateKey("6LcAsL4SAAAAAJ6iyZ60sgeyQc2YhgI9fwt5uINs");

			ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(
					remoteAddr, challenge, response);

			if (!reCaptchaResponse.isValid()) {
				session.setAttribute(CAPTCHA, false);
				result.rejectValue("captcha", "required",
						"Invalid captcha. Please try again.");
				log.debug("Invalid captcha.");
				return FORM;
			}
		}
		// }

		/* TODO: ověřit pokus (ip, čas) */
		stage.setIp(remoteAddr);
		stage.setTimeStamp();
		userStageDao.save(stage);

		/* Validate */
		ValidatorMessage message = validator.validate(stage, result);
		if (message.getResult().hasErrors()) {
			session.setAttribute(CAPTCHA, false);
			log.debug("Invalid user stage.");
			return FORM;
		}

		/* zapamatuj si, ze captcha byla ok */
		session.setAttribute(CAPTCHA, true);
		/* zobraz zpravu */
		String publicMessage;
		publicMessage = "<h2> Stage" + stage.getStageNo() + "</h2>\n" + "<b>" + message.getMessage() + "</b>\n";
		if (message.getCoords() != null)
			publicMessage = publicMessage + "<h3>" + message.getCoords()
					+ "</h3>\n";
		publicMessage = publicMessage +
		"<form></form>";
		
		model.addAttribute(MSG, publicMessage);
		return MSG;
	}
}

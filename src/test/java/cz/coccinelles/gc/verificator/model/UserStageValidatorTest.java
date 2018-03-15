package cz.coccinelles.gc.verificator.model;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import cz.coccinelles.gc.verificator.dao.UserStageValidator;

public class UserStageValidatorTest {

	@Test @Ignore
	public void userStageValidator() {
		UserStageValidator validator = new UserStageValidator();
		
		UserStage userStage;
		BindingResult result;
		ValidatorMessage message;
		
		// Wrong GC code
		System.out.println("Invalid GC wpt (aaa):");
		userStage = new UserStage("aaa", "10", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong GC code
		System.out.println("Invalid GC wpt (GCa):");
		userStage = new UserStage("GCa", "10", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong GC code
		System.out.println("Invalid GC wpt (GCaaa;):");
		userStage = new UserStage("GCaaa;", "10", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong stageNo (small)
		System.out.println("Invalid stageNo (small):");
		userStage = new UserStage("GCaaa", "0", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong stageNo (big)
		System.out.println("Invalid stageNo (big):");
		userStage = new UserStage("GCaaa", "101", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Invalid pass
		System.out.println("Invalid pass (!):");
		userStage = new UserStage("GCaaa", "10", "ccc!");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Invalid pass
		System.out.println("Invalid pass (;):");
		userStage = new UserStage("GCaaa", "10", "ccc;");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong user stage
		System.out.println("Wrong user input:");
		userStage = new UserStage("aaa;", "0", "ccc;");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(3, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Nonexisting cache
		System.out.println("Wrong user input:");
		userStage = new UserStage("GCaaa", "1", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Nonexisting stage on existing cache
		System.out.println("Wrong user input:");
		userStage = new UserStage("GC2G812", "99", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Wrong pass on existing stage
		System.out.println("Wrong user input:");
		userStage = new UserStage("GC2G812", "1", "ccc");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertTrue(message.getResult().hasErrors());
		Assert.assertEquals(1, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Stage 1
		System.out.println("Wrong user input:");
		userStage = new UserStage("GC2G812", "1", "1JBR3C");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertFalse(message.getResult().hasErrors());
		Assert.assertEquals(0, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
		
		// Stage 2
		System.out.println("Wrong user input:");
		userStage = new UserStage("GC2G812", "2", "1JBR3C5VQ4T&");
		result = new BeanPropertyBindingResult(userStage, "errors");
		message = validator.validate(userStage, result);
		System.out.println(message.getResult().toString());
		Assert.assertFalse(message.getResult().hasErrors());
		Assert.assertEquals(0, message.getResult().getErrorCount());
		Assert.assertNull(message.getCoords());
		Assert.assertNull(message.getMessage());
	};

}

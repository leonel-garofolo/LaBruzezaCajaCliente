package org.labruzeza.colectividades;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

public class PropertyResourceBundleMessageInterpolator extends ResourceBundleMessageInterpolator {
		
	public static Validator getValidation(){
		return Validation.byProvider(HibernateValidator.class)
	      .configure()	      
	      .messageInterpolator( new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator( "i18n.ValidationMessages" )))
	      .buildValidatorFactory().getValidator();
	}
}

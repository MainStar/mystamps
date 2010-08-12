package ru.mystamps.site.validators;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import ru.mystamps.db.Users;
import ru.mystamps.site.utils.Messages;

public class LoginNotExistsValidator implements Validator {
	
	private final Logger log = Logger.getRootLogger();
	
	/**
	 * Check that login does not exists in database.
	 *
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param Object value
	 * @throws ValidatorException
	 **/
	@Override
	public void validate(
			final FacesContext context,
			final UIComponent component,
			final Object value)
		throws ValidatorException {
		
		if (component instanceof EditableValueHolder) {
			if (! ((EditableValueHolder)component).isValid()) {
				return;
			}
		}
		
		try {
			final String login = (String)value;
			final Users users  = new Users();
			
			if (users.loginExists(login)) {
				final String msg =
					Messages.getTranslation(context, "tv_login_exists");
				final FacesMessage message =
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
				throw new ValidatorException(message);
			}
			
		// catch and convert exceptions from Users class to
		// ValidatorException
		} catch (NamingException ex) {
			log.error(ex);
			final String errMsg =
				Messages.getTranslation(context, "tv_internal_error");
			final FacesMessage facesMsg =
				new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
			throw new ValidatorException(facesMsg, ex);
			
		} catch (SQLException ex) {
			log.error(ex);
			final String errMsg =
				Messages.getTranslation(context, "tv_internal_error");
			final FacesMessage facesMsg =
				new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
			throw new ValidatorException(facesMsg, ex);
		}
	}
	
}

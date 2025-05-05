package org.usermanagement.validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("positiveBalanceValidator")
public class PositiveBalanceValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (value instanceof Number) {
            double balance = ((Number) value).doubleValue();

            if (balance < 0) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Invalid balance",
                        "Account balance must be a positive value"));
            }
        } else {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid input",
                    "Value must be a number"));
        }
    }
}
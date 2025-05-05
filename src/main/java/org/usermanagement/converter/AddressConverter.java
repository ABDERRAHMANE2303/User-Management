package org.usermanagement.converter;

import org.usermanagement.entities.User;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesConverter("addressConverter")
public class AddressConverter implements Converter {

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^(\\d+)\\s*-\\s*(.+)$");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        Matcher matcher = ADDRESS_PATTERN.matcher(value);
        if (matcher.matches()) {
            // This is just a dummy return because we actually handle the address
            // in multiple fields in the entity (streetNumber, streetName)
            return value;
        } else {
            throw new ConverterException("Invalid address format. Use 'Number - Street Name'");
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        // If we're displaying a full address from a User object
        if (component.getAttributes().containsKey("user") && component.getAttributes().get("user") instanceof User) {
            User user = (User) component.getAttributes().get("user");
            return user.getStreetNumber() + " - " + user.getStreetName();
        }

        // Otherwise just return the string value
        return value.toString();
    }
}
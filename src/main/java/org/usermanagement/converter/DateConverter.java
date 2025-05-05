package org.usermanagement.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            throw new ConverterException("Invalid date format. Please use " + DATE_FORMAT);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Date) {
            return sdf.format((Date) value);
        } else {
            throw new ConverterException("Object is not a Date");
        }
    }
}
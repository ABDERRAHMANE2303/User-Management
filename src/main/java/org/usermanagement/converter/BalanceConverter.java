package org.usermanagement.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@FacesConverter("balanceConverter")
public class BalanceConverter implements Converter {

    private static final String CURRENCY = "MAD";
    private final NumberFormat formatter = new DecimalFormat("#,##0.00");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        // Remove currency symbol and any non-numeric characters except '.' and '-'
        String cleanValue = value.replace(CURRENCY, "").replaceAll("[^\\d.-]", "").trim();

        try {
            return formatter.parse(cleanValue).doubleValue();
        } catch (ParseException e) {
            throw new ConverterException("Invalid number format: " + value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Number) {
            return formatter.format(value) + " " + CURRENCY;
        } else {
            throw new ConverterException("Object is not a Number");
        }
    }
}
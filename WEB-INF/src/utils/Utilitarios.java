package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;


public class Utilitarios {
		
	public String decimalFormat(String pattern, double value ) {
		DecimalFormat xdf = new DecimalFormat(pattern,new DecimalFormatSymbols(Locale.US));
		return xdf.format(value);
	}
	
	public String dateFormat(Date d){		
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	    
	    Calendar cal = new GregorianCalendar();
		cal.setTime(d);		
		return sdf.format(cal.getTime());
	}
	
	public Date parseDate(String d) throws ParseException{		
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    Date fecha =formato.parse(d);
		return fecha;
	}
			
	
}
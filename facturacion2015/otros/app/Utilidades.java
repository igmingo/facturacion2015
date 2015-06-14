package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {
	
	public static String validarStringNoNull(String campo) {
		if (estaRelleno(campo)) {
			return campo;
		} else {
			return "";
		}
	}
	
	public static String validarString(String campo) {
		if (estaRelleno(campo)) {
			return campo;
		} else {
			return null;
		}
	}
	
	public static Boolean estaRelleno(String campo) {
		if (campo == null) {
			return null;
		}
		if (campo.length()==0 ) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Integer validarEntero(String campo) {
		// Devuelve o un entero o NULL
		if (campo == null || campo.length()==0 ) {
			return null;
		}
		try {
			return Integer.parseInt(campo);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Double validarDouble(String campo) {
		if (campo == null || campo.length()==0 ) {
			return null;
		}
		try {
			return Double.parseDouble(campo);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date validarFecha(String stringFecha) {
		if (stringFecha == null || stringFecha.length()==0 ) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(stringFecha);
		} catch (ParseException e) {
			sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				return sdf.parse(stringFecha);
			} catch (ParseException e1) {
				return null;
			}
		}
	}
	
	public static String fechaAstring (Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	public static String fechaToSQL (Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fecha);
	}
	
}

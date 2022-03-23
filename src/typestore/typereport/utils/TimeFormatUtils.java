package typestore.typereport.utils;

import java.util.concurrent.TimeUnit;

public class TimeFormatUtils {
	
	public String getTimeString(int time1) {
	    long time = TimeUnit.MILLISECONDS.convert(time1, TimeUnit.SECONDS);
		long variacao = time;
	    long varsegundos = variacao / 1000L % 60L;
	    long varminutos = variacao / 60000L % 60L;
	    long varhoras = variacao / 3600000L % 24L;
	    long vardias = variacao / 86400000L % 7L;
	    String segundos = String.valueOf(varsegundos).replaceAll("-", "");
	    String minutos = String.valueOf(varminutos).replaceAll("-", "");
	    String horas = String.valueOf(varhoras).replaceAll("-", "");
	    String dias = String.valueOf(vardias).replaceAll("-", "");
	    if (dias.equals("0") && horas.equals("0") && minutos.equals("0"))
	      return String.valueOf(segundos) + "s"; 
	    if (dias.equals("0") && horas.equals("0"))
	      return String.valueOf(minutos) + "m " + segundos + "s"; 
	    if (dias.equals("0"))
	      return String.valueOf(horas) + "h " + minutos + "m " + segundos + "s"; 
	    return String.valueOf(dias) + "d " + horas + "h " + minutos + "m " + segundos + "s";
	  }

}

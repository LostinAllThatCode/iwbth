package org.gdesign.iwbth.game.main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {

	public static String getTimeStamp(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss "); 
		return sdf.format(date);
	}
}

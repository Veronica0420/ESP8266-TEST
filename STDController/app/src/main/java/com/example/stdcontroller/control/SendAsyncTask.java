package com.example.stdcontroller.control;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import android.os.AsyncTask;

public class SendAsyncTask extends AsyncTask<String, Void, Void> {
	private static final String IP = "192.168.4.1";
	private static final int PORT = 333;

	private Socket client = null;
	private PrintStream out = null;
 
 
	@Override
	protected Void doInBackground(String... params) {
		String str = params[0];
		try {
			client = new Socket(IP, PORT);
			client.setSoTimeout(5000);

			out = new PrintStream(client.getOutputStream());
			out.print(str);
			out.flush();
 
			if (client == null) {
				return null;
			} else {
				out.close();
				client.close();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return null;
	}
	
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {

	OutputStream outputStream = null;
	OutputStreamWriter outputStreamWriter = null;
	BufferedWriter bufferedWriter = null;

	InputStream inputStream = null;
	InputStreamReader inputStreamReader = null;
	BufferedReader bufferedReader = null;

	public SocketClient(Socket socket) throws IOException {
		this.outputStream = socket.getOutputStream();
		this.outputStreamWriter = new OutputStreamWriter(outputStream);
		this.bufferedWriter = new BufferedWriter(outputStreamWriter); //서버로 전송을 위한 OutputStream

		this.inputStream = socket.getInputStream();
		this.inputStreamReader = new InputStreamReader(inputStream);
		this.bufferedReader = new BufferedReader(inputStreamReader); // 서버로부터 Data를 받음
		// sendData(message);
		// close();
		// receiveData();
	}

	public String sendAndReceiveData(String data) throws IOException {
		sendData(data);
		return receiveData();
	}

	public void sendData(String data) throws IOException{
		// sendData
		bufferedWriter.write(data);
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}
	
	public String receiveData() throws IOException {
		return this.bufferedReader.readLine();
	}

	public void close() throws IOException {
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();

		bufferedWriter.close();
		outputStreamWriter.close();
		outputStream.close();
	}

}

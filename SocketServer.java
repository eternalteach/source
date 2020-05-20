import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketServer {

	private Map<String, String> workProgressMap = new HashMap<>();

	public void ServerRun() throws IOException {

		// 주의 Try With Resource 구문 사용하면 소켓 서버 내려간다.
		int port = 11111;
		ServerSocket server = null;
		Socket socket = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			server = new ServerSocket(port);
			while (true) {
				System.out.println("========== 접속 대기중 ==========");
				socket = server.accept(); // 클라이언트가 접속하면 통신할 수 있는 소켓 반환

				System.out.println("==========" + socket.getInetAddress() + " 연결 ==========");

				// 클라이언트로부터 데이터를 받기 위한 InputStream 선언
				inputStream = socket.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				bufferedReader = new BufferedReader(inputStreamReader);

				String data = null;
				data = bufferedReader.readLine();
				System.out.println("클라이언트로 부터 받은 데이터: " + data);

				// TODO 추후 객체화하자
				String[] dataInfo = data.split("\\|"); // receiver | workProgress
				String receiver = dataInfo[0];
				String receiveWorkProgress = dataInfo[1];

				// 진행정도 저장
				workProgressMap.put(receiver, receiveWorkProgress);

				String sendWorkProgress = null;
				// 수신자에게 메세지 내용 전달
				if (workProgressMap.containsKey(receiver)) {
					sendWorkProgress = workProgressMap.get(receiver);
					receiveData(sendWorkProgress, socket);
					System.out.println("========== 전송   완료 ==========");
				}
			}
			// TODO 주기적으로 roomMap 에 있는 소켓들 중 미사용 소켓 제거해야한다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 데이터 송신
	public void receiveData(String data, Socket socket) throws IOException {
		try (OutputStream outputStream = socket.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);) 
		{
			bufferedWriter.write(data); // 클라이언트로 데이터 전송
			bufferedWriter.flush();
		}

	}
}

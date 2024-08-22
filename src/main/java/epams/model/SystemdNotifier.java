package epams.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SystemdNotifier {

    private void sendNotification(String message) throws IOException {
        String notifySocket = System.getenv("NOTIFY_SOCKET");
        if (notifySocket == null) {
            throw new IOException("NOTIFY_SOCKET environment variable not set");
        }

        UnixSocketAddress address = new UnixSocketAddress(new File(notifySocket));
        try (UnixSocketChannel channel = UnixSocketChannel.open(address)) {
            channel.write(StandardCharsets.UTF_8.encode(message + "\n"));
        }
    }

    @Scheduled(fixedRate = 30000)  // 30초마다 실행
    public void notifyWatchdog() {
        try {
            sendNotification("WATCHDOG=1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
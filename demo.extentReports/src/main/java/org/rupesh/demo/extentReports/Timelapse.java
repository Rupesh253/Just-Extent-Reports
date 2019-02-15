package org.rupesh.demo.extentReports;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

//import org.jcodec.api.awt.SequenceEncoder;
import org.jcodec.api.*;
import org.jcodec.common.model.Picture;

import lombok.SneakyThrows;

public class Timelapse {

	private static final int[] colors = { 0xFFFFFF, 0xE4E4E4, 0x888888, 0x222222, 0xffa7d1, 0xe50000, 0xe59500,
			0xa06a42, 0xe5d900, 0x94e044, 0x02be01, 0x00d3dd, 0x0083c7, 0x0000ea, 0xcf6ee4, 0x820080 };

	private static final Queue<BufferedImage> imageQueue = new ArrayDeque<>();
	private static final TimerTask getImageTask = new TimerTask() {

		@Override
		@SneakyThrows
		public void run() {
			System.out.println("Reading new image...");

			InputStream stream;
			try {
				stream = new URL("https://www.reddit.com/api/place/board-bitmap").openStream();
				byte[] data = new byte[500000];
				int read = 0;
				while (read < 500000) {
					read += stream.read(data, read, Math.min(500000 - read, 1000));
				}
				BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < data.length; i++) {
					int b = data[i] & 0xFF;
					int c1 = b >>> 4;
					int c2 = b & 0xF;
					int x = (i * 2) % 1000;
					int y = (i * 2) / 1000;
					image.setRGB(x, y, colors[c1]);
					image.setRGB(x + 1, y, colors[c2]);
				}

				imageQueue.offer(image);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {

			}

		}
	};

	private static boolean done = false;

	@SneakyThrows
	public static void main(String[] args) throws IOException, InterruptedException {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(getImageTask, 0, TimeUnit.SECONDS.toMillis(10));

		new Thread(() -> {
			Scanner scan = new Scanner(System.in);
			while (true) {
				String in = scan.nextLine();
				if (in.equals("stop")) {
					System.out.println("Stopping!");
					done = true;
					scan.close();
					return;
				}
			}
		}, "Standard Input Reader Thread").start();

		File output = new File("timelapse.mp4");
		SequenceEncoder enc = new SequenceEncoder(output);

		while (!done) {
			BufferedImage img;
			if ((img = imageQueue.poll()) != null) {
				System.out.println("Encoding new image...");
				// enc.encodeImage(img);
				// enc.encodeNativeFrame(img);
			} else {
				Thread.sleep(TimeUnit.SECONDS.toMillis(30));
			}
		}

		enc.finish();
		System.exit(0);
	}
}

import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.apache.commons.io.*;
import org.apache.http.*;

public class BenchMark {
	public ProcessBuilder ps;
	private double avg;
	private int avgCount;
	private Process _proc;
	// spelling
	private char hashDenomination;

	public BenchMark(ProcessBuilder p) {
		this.ps = p;
		avg = 0;
		avgCount = 0;
		hashDenomination = '0';
	}

	public void stop() {

		_proc.destroy();
	}

	public double start() {
		avg = 0;
		try {
			ps.redirectErrorStream(true);
			Process pr = ps.start();
			_proc = pr;

			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (!line.contains("/s")) {
					continue;
				}
				String[] split = line.split(" ");
				for (String s : split) {
					// System.out.println(s + " ");
					if (isDouble(s)) {
						if (avgCount == 0) {
							System.out.print(s + " ");
							avg += Double.parseDouble(s);
							avgCount++;
						} else {
							System.out.print(s + " ");
							avg += Double.parseDouble(s);
							avg /= 2;
							avgCount++;
						}
					} else if (s.contains("H/s")) {
						System.out.println(s);
						hashDenomination = s.toUpperCase().charAt(0);

					}
				}
			}

			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return avg;
	}

	public double getAvg() {
		return avg;
	}

	public int getAvgCount() {
		return avgCount;

	}

	public char getHashrateD() {
		// spelling
		return hashDenomination;

	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

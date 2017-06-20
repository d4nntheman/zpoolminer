import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.apache.commons.io.*;
import org.apache.http.*;
public class ZPoolMiner {
	public static String BTCAddress = "1Ju5Z88SHyu9yFhiX5tsxrWyC5EMbH38yN";

	public static void main(String[] args) {
		AlgoStats algoHandle = new AlgoStats();

        if(!loadBenchmarkStats(algoHandle)){
		    benchMarkAllAlgos(algoHandle);
            saveBenchmarkStats(algoHandle);
        }
        System.out.println("**********************");
        for(String s: getAlgoList()){
            System.out.println(s + " "  + getAlgoJsonObject(s,algoHandle).getGpuHashrate() 
                    + getAlgoJsonObject(s,algoHandle).getGpuHashrateDenom() + "/s"
                    + " * " + getAlgoJsonObject(s,algoHandle).getActualLast24h());
            System.out.println("= " + getProfit(s,algoHandle));
            System.out.println();

        }

	}

    public static void saveBenchmarkStats(AlgoStats AH){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream("test1.txt"));
            for(String s: getAlgoList()){
               out.writeDouble(getAlgoJsonObject(s,AH).getGpuHashrate()); 
               out.writeChar(getAlgoJsonObject(s,AH).getGpuHashrateDenom()); 

            }
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean loadBenchmarkStats(AlgoStats AH){
        File f = new File("test1.txt");
        if(!f.exists()){
            return false;
        }
        try {
            DataInputStream in = new DataInputStream (new FileInputStream("test1.txt"));
            for(String s: getAlgoList()){
               getAlgoJsonObject(s,AH).setGpuHashrate(in.readDouble());
               getAlgoJsonObject(s,AH).setGpuHashrateDenom(in.readChar());
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static double getProfit(String algo,AlgoStats AH){
        double multiplyer = getProfitMultiplyer(algo,getAlgoJsonObject(algo,AH).getGpuHashrateDenom());
        double hashrate = getAlgoJsonObject(algo,AH).getGpuHashrate();
        double profit =   getAlgoJsonObject(algo,AH).getActualLast24h();

        return multiplyer * hashrate * profit;
    }

	public static void benchMarkAllAlgos(AlgoStats algoHandle) {
		for (String s : getAlgoList()) {
			ProcessBuilder ps = getProcessBuilderForAlgo(s, algoHandle);
			System.out.println(s + "current profitability is " + getAlgoJsonObject(s, algoHandle).getEstimateCurrent());
			double a;
			BenchMark bm = new BenchMark(ps);
			Thread t = new Thread(new Runnable() {
				public void run() {
					bm.start();
				}
			});
			System.out.println("created");
			t.start();
			System.out.println("started");
			while (bm.getAvgCount() < 4) {
				try {
					System.out.println("Benching " + s);
					Thread.sleep(5000);
				} catch (Exception e) {

				}
			}

			double u = bm.getAvg();
			getAlgoJsonObject(s, algoHandle).setGpuHashrate(u);
			getAlgoJsonObject(s, algoHandle).setGpuHashrateDenom(bm.getHashrateD());
			System.out.println(s + "AVG hashrate is " + u + bm.getHashrateD()+ "/s");
			t.interrupt();
			bm.stop();

			while (!t.isInterrupted()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				System.out.println("Waiting for thread to die");
			}
			System.out.println("Done");
		}
	}

	public static Algo getAlgoJsonObject(String algo, AlgoStats AH) {
		switch (algo) {
		case "m7m":
		case "equihash":
		case "neoscrypt":
			return AH.getRootJSON().getNeoscrypt();
		case "xevan":
			return AH.getRootJSON().getXevan();
		case "hmq1725":
			return AH.getRootJSON().getHmq1725();
		case "x17":
		case "blake2s":
			return AH.getRootJSON().getBlake2s();
		case "blakecoin":
			return AH.getRootJSON().getBlakecoin();
		case "decred":
			return AH.getRootJSON().getDecred();
		case "c11":
		case "sib":
		case "x11evo":
			return AH.getRootJSON().getX11evo();
		case "yescrypt":
		case "lyra2v2":
			return AH.getRootJSON().getLyra2v2();
		case "scrypt":
		case "x11":
		case "veltor":
		case "groestl":
		case "nist5":
		case "myrgr":
		case "qubit":
		case "bitcore":
			return AH.getRootJSON().getBitcore();
		case "quark":
		case "x13":
		case "x14":
		case "timetravel":
			return AH.getRootJSON().getTimetravel();
		case "lbry":
			return AH.getRootJSON().getLbry();
		case "skein":
			return AH.getRootJSON().getSkein();
		case "x15":
		case "sha256":
		}
		return null;
	}

	public static ProcessBuilder getProcessBuilderForAlgo(String algo, AlgoStats algoHandle) {
		switch (algo) {
		case "m7m":
		case "equihash":
		case "neoscrypt":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "neoscrypt", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getNeoscrypt().getPort(),
					"-u", BTCAddress, "-p", "x");
		case "xevan":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "xevan", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getXevan().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "hmq1725":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "hmq1725", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getHmq1725().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "x17":
		case "blake2s":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "blake2s", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getBlake2s().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "blakecoin":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "blakecoin", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getBlakecoin().getPort(),
					"-u", BTCAddress, "-p", "x");
		case "decred":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "decred", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getDecred().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "c11":
		case "sib":
		case "x11evo":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "x11evo", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getX11evo().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "yescrypt":
		case "lyra2v2":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "lyra2v2", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getLyra2v2().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "scrypt":
		case "x11":
		case "veltor":
		case "groestl":
		case "nist5":
		case "myrgr":
		case "qubit":
		case "bitcore":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "bitcore", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getBitcore().getPort(), "-u",
					BTCAddress, "-p", "x");

		case "quark":
		case "x13":
		case "x14":
		case "timetravel":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "timetravel", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getTimetravel().getPort(),
					"-u", BTCAddress, "-p", "x");
		case "lbry":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "lbry", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getLbry().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "skein":
			return new ProcessBuilder("./bin/ccminer_tp", "-a", "skein", "-o",
					"stratum+tcp://" + algo + ".mine.zpool.ca:" + algoHandle.getRootJSON().getSkein().getPort(), "-u",
					BTCAddress, "-p", "x");
		case "x15":
		case "sha256":
		}
		return null;
	}
    public static String [] getAlgoList(){

        String[] algoList = {
				// "m7m",
				// "equihash",
				"neoscrypt",
				// "xevan", //Build ccminer for xevan tp !have
				"hmq1725",
				// "x17",
				"blake2s", "blakecoin", "decred",
				// "c11",
				// "sib",
				"x11evo",
				// "yescrypt",
				"lyra2v2",
				// "scrypt",
				// "x11",
				// "veltor",
				// "groestl",
				// "nist5",
				// "myrgr",
				// "qubit",
				"bitcore",
				// "quark",
				// "x13",
				// "x14",
				"timetravel", "lbry", "skein",
				// "x15",
				// "sha256"
		};
        return algoList;
    }

    public static double getProfitMultiplyer(String algo, char hashD){
    //* values in mBTC/MH/day, per GH for sha256 & blake algos, kSol. for equihash
        switch(algo){
            //MH
            case "neoscrypt":
            case "hmq1725":
            case "x11evo":
            case "lyra2v2":
            case "bitcore":
			case "timetravel":
            case "lbry": 
            case "skein":
                switch(hashD){
                    case 'M':
                        return 1;
                    case 'G':
                        return 10;
                    case 'K':
                        return .01;
                }
                break;
				//case "m7m",
				// "equihash",
				// "xevan",
				// "x17",
            case "blake2s":
            case "blakecoin": 
            case "decred":
                switch(hashD){
                    case 'M':
                        return .01;
                    case 'G':
                        return 1;
                    case 'K':
                        return .001;
                }
                break;
				// "c11",
				// "sib",
				// "yescrypt",
				// "scrypt",
				// "x11",
				// "veltor",
				// "groestl",
				// "nist5",
				// "myrgr",
				// "qubit",
				// "quark",
				// "x13",
				// "x14",
				// "x15",
				// "sha256"
		}
        return 0;
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
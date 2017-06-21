import com.google.gson.*;
import org.apache.commons.io.*;
import org.apache.http.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class AlgoStats {
	public AlgoStats() {
		updateAlgos();
	}

	private RootJSON algos;

	private String getJson(String sURL) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(sURL);
			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)"
					+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29" + "Safari/537.36");
			reader = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public void updateAlgos() {
        //algos = null;
		String url = "http://www.zpool.ca/api/status";
		String json = null;
		try {
			json = getJson(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
        if(json != null && !json.isEmpty() && !json.equals(" "))
		    algos = gson.fromJson(json, RootJSON.class);
	}

	public String getBestAlgo() {
		return null;
	}

	public RootJSON getRootJSON() {
		return algos;
	}

	public class RootJSON {
		private Algo bitcore;

		public Algo getBitcore() {
			return this.bitcore;
		}

		public void setBitcore(Algo bitcore) {
			this.bitcore = bitcore;
		}

		private Algo blake2s;

		public Algo getBlake2s() {
			return this.blake2s;
		}

		public void setBlake2s(Algo blake2s) {
			this.blake2s = blake2s;
		}

		private Algo blakecoin;

		public Algo getBlakecoin() {
			return this.blakecoin;
		}

		public void setBlakecoin(Algo blakecoin) {
			this.blakecoin = blakecoin;
		}

		private Algo c11;

		public Algo getC11() {
			return this.c11;
		}

		public void setC11(Algo c11) {
			this.c11 = c11;
		}

		private Algo decred;

		public Algo getDecred() {
			return this.decred;
		}

		public void setDecred(Algo decred) {
			this.decred = decred;
		}

		private Algo equihash;

		public Algo getEquihash() {
			return this.equihash;
		}

		public void setEquihash(Algo equihash) {
			this.equihash = equihash;
		}

		private Algo groestl;

		public Algo getGroestl() {
			return this.groestl;
		}

		public void setGroestl(Algo groestl) {
			this.groestl = groestl;
		}

		private Algo hmq1725;

		public Algo getHmq1725() {
			return this.hmq1725;
		}

		public void setHmq1725(Algo hmq1725) {
			this.hmq1725 = hmq1725;
		}

		private Algo lbry;

		public Algo getLbry() {
			return this.lbry;
		}

		public void setLbry(Algo lbry) {
			this.lbry = lbry;
		}

		private Algo lyra2v2;

		public Algo getLyra2v2() {
			return this.lyra2v2;
		}

		public void setLyra2v2(Algo lyra2v2) {
			this.lyra2v2 = lyra2v2;
		}

		private Algo m7m;

		public Algo getM7m() {
			return this.m7m;
		}

		public void setM7m(Algo m7m) {
			this.m7m = m7m;
		}

		//@SerializedName("myr-gr")
		private Algo myrgr;

		public Algo getMyrGr() {
			return this.myrgr;
		}

		public void setMyrGr(Algo myrgr) {
			this.myrgr = myrgr;
		}

		private Algo neoscrypt;

		public Algo getNeoscrypt() {
			return this.neoscrypt;
		}

		public void setNeoscrypt(Algo neoscrypt) {
			this.neoscrypt = neoscrypt;
		}

		private Algo nist5;

		public Algo getNist5() {
			return this.nist5;
		}

		public void setNist5(Algo nist5) {
			this.nist5 = nist5;
		}

		private Algo quark;

		public Algo getQuark() {
			return this.quark;
		}

		public void setQuark(Algo quark) {
			this.quark = quark;
		}

		private Algo qubit;

		public Algo getQubit() {
			return this.qubit;
		}

		public void setQubit(Algo qubit) {
			this.qubit = qubit;
		}

		private Algo scrypt;

		public Algo getScrypt() {
			return this.scrypt;
		}

		public void setScrypt(Algo scrypt) {
			this.scrypt = scrypt;
		}

		private Algo sha256;

		public Algo getSha256() {
			return this.sha256;
		}

		public void setSha256(Algo sha256) {
			this.sha256 = sha256;
		}

		private Algo sib;

		public Algo getSib() {
			return this.sib;
		}

		public void setSib(Algo sib) {
			this.sib = sib;
		}

		private Algo skein;

		public Algo getSkein() {
			return this.skein;
		}

		public void setSkein(Algo skein) {
			this.skein = skein;
		}

		private Algo timetravel;

		public Algo getTimetravel() {
			return this.timetravel;
		}

		public void setTimetravel(Algo timetravel) {
			this.timetravel = timetravel;
		}

		private Algo veltor;

		public Algo getVeltor() {
			return this.veltor;
		}

		public void setVeltor(Algo veltor) {
			this.veltor = veltor;
		}

		private Algo x11;

		public Algo getX11() {
			return this.x11;
		}

		public void setX11(Algo x11) {
			this.x11 = x11;
		}

		private Algo x11evo;

		public Algo getX11evo() {
			return this.x11evo;
		}

		public void setX11evo(Algo x11evo) {
			this.x11evo = x11evo;
		}

		private Algo x13;

		public Algo getX13() {
			return this.x13;
		}

		public void setX13(Algo x13) {
			this.x13 = x13;
		}

		private Algo x14;

		public Algo getX14() {
			return this.x14;
		}

		public void setX14(Algo x14) {
			this.x14 = x14;
		}

		private Algo x15;

		public Algo getX15() {
			return this.x15;
		}

		public void setX15(Algo x15) {
			this.x15 = x15;
		}

		private Algo x17;

		public Algo getX17() {
			return this.x17;
		}

		public void setX17(Algo x17) {
			this.x17 = x17;
		}

		private Algo xevan;

		public Algo getXevan() {
			return this.xevan;
		}

		public void setXevan(Algo xevan) {
			this.xevan = xevan;
		}

		private Algo yescrypt;

		public Algo getYescrypt() {
			return this.yescrypt;
		}

		public void setYescrypt(Algo yescrypt) {
			this.yescrypt = yescrypt;
		}
	}
}

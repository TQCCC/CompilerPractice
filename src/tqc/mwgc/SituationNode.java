package tqc.mwgc;

import java.util.Arrays;

/*
 * SituationNode:
 * It shows a kind of situation:
 * For example:
 * here:array: MAN WOLF GOLF CABBAGE; there:array: 
 * means all the objects are right here, nobody gets through the river.
 * 
 * here: WOLF CABBAGE; there:
 * */

public class SituationNode {

	private StringBuilder here;
	private StringBuilder there;

	public SituationNode(String h, String t) {
		here = new StringBuilder(sortByASCII(h));
		there = new StringBuilder(sortByASCII(t));
	}

	public static String sortByASCII(String str) {
		int[] arr = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			arr[i] = (int) str.charAt(i);
		}
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append((char) arr[i]);
		}
		return sb.toString();
	}

	public SituationNode() {
		this("mwgc", "");
	}

	/**
	 * Move the given arr to the other site, according to the boolean parameter
	 * 
	 * @param h
	 * Objects be moved. One or one more.
	 * @param direct 
	 * <br>true: move to there from here.
	 * <br>false: move to here from there.
	 */

	public SituationNode move(String h, boolean direct) {
		if (h.length() > 2 || h.length() <= 0) {
			return this;
		}
		if (h.length() <= 2 && h.indexOf("m") == -1) {
			return this;
		}

		StringBuilder sbhere = new StringBuilder(this.here.toString());
		StringBuilder sbthere = new StringBuilder(this.there.toString());

		for (int i = 0; i < h.length(); i++) {
			sbhere = new StringBuilder(sbhere.toString());
			sbthere = new StringBuilder(sbthere.toString());

			char ch = h.charAt(i);

			int idx = direct ? sbhere.indexOf("" + ch) : sbthere.indexOf("" + ch);
			if (idx == -1) {
				return this;
			}

			if (direct) {
				sbhere.delete(idx, 1 + idx); // Attetion: 1+idx
				sbthere.append(ch);
			} else {
				sbthere.delete(idx, 1 + idx); // Attetion: 1+idx
				sbhere.append(ch);
			}

			/* Sort. */
			sbhere = new StringBuilder(sortByASCII(sbhere.toString()));
			sbthere = new StringBuilder(sortByASCII(sbthere.toString()));

		}

		if (sbhere.indexOf("m") == -1 && ((sbhere.indexOf("w") != -1 && sbhere.indexOf("g") != -1)
				|| (sbhere.indexOf("g") != -1 && sbhere.indexOf("c") != -1))) {
			return this;
		}
		if (sbthere.indexOf("m") == -1 && ((sbthere.indexOf("w") != -1 && sbthere.indexOf("g") != -1)
				|| (sbthere.indexOf("g") != -1 && sbthere.indexOf("c") != -1))) {
			return this;
		}

		return new SituationNode(sbhere.toString(), sbthere.toString());
	}

	/*
	 * To show onto the console.
	 */
	public SituationNode show() {
		System.out.println("here: " + here.toString() + "||there: " + there.toString());
		return this;
	}

	public StringBuilder getHere() {
		return here;
	}

	public StringBuilder getThere() {
		return there;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SituationNode) {
			SituationNode anosnode = (SituationNode) obj;
			if ((here.toString().equals(anosnode.getHere().toString()))
					&& (there.toString().equals(anosnode.getThere().toString()))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "here: " + here.toString() + "|there: " + there.toString();
	}

}

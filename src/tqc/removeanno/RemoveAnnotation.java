package tqc.removeanno;

import java.io.*;

/**
 * 
 * First step of a compiler: 
 * Remove annotations from the source file.
 * 
 * @author TQC
 * */

public class RemoveAnnotation {

	public static byte[] getBytesFromAFile(String fileName) throws IOException {

		File file = new File(fileName);

		if (!file.exists()) {
			return null;
		}

		FileInputStream fis = new FileInputStream(file);
		int len = (int) file.length();
		byte[] bs = new byte[len];
		fis.read(bs, 0, bs.length);
		fis.close();

		return bs;
	}

	public static int getNumberOfWords(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			return -1;
		}
		FileInputStream fis = new FileInputStream(file);
		
		int count = 0;
		int flag = 1;
	
		int b = 0;
		while ((b = fis.read()) != -1) {
			if (b == 32 || b == 13 || b == 10 || b == 9) {
				flag = 1;
				continue;
			} else {
				if (flag == 1) {
					flag = 0;
					count++;
				}
			}
		}
		fis.close();
		return count;
	}

	public static int hasSubString(String str, String t) {

		int i = 0, j = 0;
		while (i < str.length() && j < t.length()) {
			if (str.charAt(i) == t.charAt(j)) {
				i++;
				j++;
			} else {
				i = i - j + 1;
				j = 0;
			}
		}

		if (j >= t.length()) {
			return i - t.length();
		} else {
			return -1;
		}
	}

	/**
	 * @author TQC
	 * 1.Think of first type of annotation of double forward slashes. 
	 * 2.Think of second type of annotation of a forward slash and a asterisk.
	 * 3.Think of annotation in annotation.
	 * 4.Think of annotation in quotation marks.
	 * */

	public static void removeAnnotations(String fileName, String targetFileName) throws IOException {
		
		int dquo = 34;
		int squo = 39;
		int[] annoleft = { 47, 42 };
		int[] annoright = { 42, 47 };
		int[] doublefslash = { 47, 47 };
		int[] enterline = { 13, 10 };

		File sourceFile = new File(fileName);
		if (!sourceFile.exists()) {
			return;
		}
		FileInputStream fis = new FileInputStream(sourceFile);
		File targetFile = new File(targetFileName);
		
		RandomAccessFile fos = new RandomAccessFile(targetFile, "rw");
		
		int pre = 0;
		int current = 0;
		int flag = -1;
		/*
		 * flag:
		 * -1: default;
		 * 0: in double fslash;
		 * 1: in annotation left and annotation right;
		 * 3: in single quotation;
		 * 4: in double quotation;
		 * */
		while((current = fis.read())!=-1) {
			
			switch(flag){
			case 0:{	
				//End of this case.
				if ((pre == enterline[0] && current == enterline[1])) {flag = -1;}
			}
				break;
			case 1:{				//annotation left and annotation right
				if ((pre == annoright[0] && current == annoright[1])) {flag = -1;}
			}
				break;
			case 3:{				//single quotation
				if (current == squo) {flag = -1;}
				// Delete \t, \n, \r in quotation.
				if (current!=9 && current!=10 && current!=13) {
					fos.write((char)current);
				}
			}
				break;
			case 4:{				//double quotation
				if (current == dquo) {flag = -1;}
				if (current!=9 && current!=10 && current!=13) {
					fos.write((char)current);
				}
			}
				break;
			default:{		
				//Default characristics, flag==-1.
//				if (current!=9 && current!=10 && current!=32 && current!=13) {
					fos.write((char)current);
//				}
				
				if (pre == doublefslash[0] && current == doublefslash[1]) {
					// Found a double fslash.
					flag = 0;
					fos.seek(fos.getFilePointer()-2);
				}else if (pre == annoleft[0] && current == annoleft[1]) {
					// Found a annotation left.
					flag = 1;
					fos.seek(fos.getFilePointer()-2);
				}else if (current == squo) {
					// Found a single quotation.
					flag = 3;
				}else if (current == dquo) {
					// Found a double quotation.
					flag = 4;
				}
				
			}
				break;
			}
			
			pre = current;
			
		}// End of while
		
		fis.close();
		fos.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.println("p1 App starts...");

		removeAnnotations("./main.c", "./target.c");

	}

}

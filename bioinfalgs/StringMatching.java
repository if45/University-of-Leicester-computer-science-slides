/**
 * Class StringMatching
 *
 * searching occurrences of a pattern in a text
 *
 * (all methods are static)
 *
 * Thomas Erlebach
 *
 */
import java.io.*;

class StringMatching {

  /*
   * search pattern P in text T
   * and report all occurrences
   *
   * (implements a simple quadratic-time algorithm)
   */
  public static void search(String P, String T) {
    int m = P.length();
    int n = T.length();
    for (int i = 0; i<=n-m; i++) {
      boolean f=true;
      for (int j=0; j<m; j++) {
        if (P.charAt(j)!=T.charAt(i+j)) {
          f=false;
          break;
        }
      }
      if (f)
        System.out.println("pattern occurs in position "+i);
    }
  }

  static int[] computePrefixFunction(String P) {
	  int m = P.length();
	  int[] pi = new int[m+1];
	  pi[1]=0;
	  int k=0;
	  for (int q=2; q<=m; q++) {
		  while (k>0 && P.charAt(k)!=P.charAt(q-1)) {
			   k = pi[k];
		  }
		  if (P.charAt(k)==P.charAt(q-1)) k++;
		  pi[q]=k;
	  }
	  return pi;
  }
  /*
   * Knuth-Morris-Pratt string matching
   *
   */
  public static void KMPsearch(String P, String T) {
	  int m = P.length();
	  int n = T.length();
	  int q = 0;
	  int[] pi = computePrefixFunction(P);
	  for (int i=0; i<n; i++) {
		  while (q>0 && P.charAt(q) != T.charAt(i)) q = pi[q];
		  if (P.charAt(q) == T.charAt(i)) q++;
		  if (q==m) {
                      System.out.println("pattern occurs in position "+(i-m+1));
		      q=pi[q];
		  }
	  }
  }
  
  /*
   * main method
   *
   * This method reads the pattern P from pfile and the text T from
   * tfile, and then calls search(P,T) or KMPsearch(P,T) to search
   * all occurrences of P in T.
   */
  public static void main(String[] Args)  throws IOException {
    BufferedReader in;

    String tfile="bigtext.txt"; // filename of text
    String pfile="bigpattern.txt"; // filename of pattern

    // read pattern file
    System.out.println("Reading pattern from file "+pfile);
    in = new BufferedReader(new FileReader(pfile));
    String P = in.readLine();

    // read text file
    System.out.println("Reading text from file "+tfile);
    in = new BufferedReader(new FileReader(tfile));
    String T = in.readLine();

    System.out.println("Running KMP");
    KMPsearch(P,T);

    System.out.println();
    System.out.println("Running Naive Search");
    search(P,T);
  }
};

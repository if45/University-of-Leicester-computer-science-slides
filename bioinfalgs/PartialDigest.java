// Partial Digest Problem
// Algorithms for Bioinformatics
//
import java.io.*;
import java.util.*;

class PartialDigest {
	// returns the largest Integer from a List of Integer-objects
	static int max(List<Integer> l) {
		ListIterator<Integer> it = l.listIterator();
		int largest = 0;
		while (it.hasNext()) {
			int x = it.next();
			if (x > largest)
				largest = x;
		}
		return largest;
	}

	// Method place()
	// tries to extend partial solution points with unused distances dist
	// into a complete solution
	// Parameters:
	//    L: list of unused distances
	//    X: list of points that have already been determined
	//    M: rightmost point
	//
	// When this method is called first, X contains the
	// points 0 and M, and L contains all distances except M
	//
	static void place(List<Integer> L, List<Integer> X, int M) {
		if (L.size()==0) {
			System.out.println("Potential solution:");
			Collections.sort(X);
			displayList(X);
		}
		else {
			int [] cand = new int[2];
			cand[0] = max(L);
			cand[1] = M-cand[0];
			for (int i=0; i<2; i++) {
			    if (! X.contains(cand[i])) {
				// try adding point cand[i]
				LinkedList<Integer> remainingDist = new LinkedList<Integer>(); // copy it
				ListIterator<Integer> it = X.listIterator();
				boolean works = true;
				while (it.hasNext()) {
					int p = it.next();
					int d = p-cand[i];
					if (d<0) d = -d;
					if (! L.contains(d)) {
						works=false;
						break;
					}
					remainingDist.add(d);
					L.remove(new Integer(d));
				}
				if (works) {
					X.add(cand[i]);
					place(L,X,M);
					X.remove(new Integer(cand[i]));
				}
				it = remainingDist.listIterator();
				while (it.hasNext()) {
					int x = it.next();
					L.add(x);
				}
			    }
			}
		}
	}

	// Skiena's algorithm for the Partial Digest Problem
	// Parameter: L is the list of distances
	static void compute(List<Integer> L) {
		// X will store the partial solutions
		LinkedList<Integer> X = new LinkedList<Integer>();

		// first we put 0 and M into the list X
		X.add(0);
		int M = max(L);
		X.add(M);

		// remove M from L
		L.remove(new Integer(M));

		// now call the recursive place method
		place(L,X,M);
	}

	// method to display all elements of a List of integers
	// (can be used to output a solution)
	static void displayList(List<Integer> l) {
			ListIterator<Integer> it = l.listIterator();
		        while (it.hasNext()) {
				int x = it.next();
				System.out.print(" "+x);
			}
			System.out.println();
	}

	/* main method
	 *
	 * - reads distances from input file "data.txt" and stores them
	 *   in a LinkedList
	 * - sorts the distances
	 * - calls compute() to find and output all solutions
	 */
	public static void main(String[] Args) throws IOException {

		// use linked list to store the n(n-1)/2 distances
		LinkedList<Integer> distances = new LinkedList<Integer>();

		// read distances from input file
		BufferedReader in = new BufferedReader(new FileReader("data150.txt"));
		int m=0;
		for (;;m++) {
			String X = in.readLine();
			if (X==null) break;
			distances.add(Integer.valueOf(X));
		}
		int n = (int) ((-1+Math.sqrt(1+8*m))/2+1); // compute n

		// check whether number of distances is meaningful
		if (n*(n-1)/2!=m) {
			System.out.println("Error: number of distances is not n(n-1)/2");
			System.exit(1);
		}

		Collections.sort(distances);

		System.out.println("Input data consists of "+m+" distances, solutions will consist of "+n+" points");

		// compute all solutions to PDP problem and output them

		System.out.println("Computation begins.");

		compute(distances);

		System.out.println("Computation has finished.");
	}
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * 
 * @author fadihariri
 *BackEnd for Ahot's app
 *services privoded: transcription, translation, mutate
 */
public class DNATools {
	public static void main(String[] args) {
        
		// TODO Auto-generated method stub
        DNATools tool = new DNATools();
		String dna = "gatctacg";
        String rna =transcribe(dna, 1);
		System.out.println (rna);
		System.out.println(reverseComplement (dna));
		//System.out.println (translate (true, rna, 1));
		ArrayList <String> arr = translateAll (true, rna);
		for (String frame : arr)System.out.println (frame);
         
	}
	//initialization of variables
	public DNATools (){
		String [] nucleotides = {"a", "c", "g", "t"};
		String [] compNuc = { "t", "g", "c", "a"};
		String [] com = {"u", "g", "c", "a"};
		String [][] codons ={{"GCU", "GCC", "GCA", "GCG"}, {"CGU", "CGC", "CGA", "CGG", "AGA", "AGG"}, {"AAU", "AAC"}, 
				{"GAU", "GAC"}, {"UGU", "UGC"}, {"CAA", "CAG"}, {"GAA", "GAG"}, {"GGU", "GGC", "GGA", "GGG"},
				{"CAU", "CAC"}, {"AUU", "AUC", "AUA"}, {"AUG"}, {"UUA", "UUG", "CUU", "CUC", "CUA", "CUG"}, 
				{"AAA", "AAG"}, {"UUU", "UUC"}, {"CCU", "CCC", "CCA", "CCG"}, {"UCU", "UCC", "UCA", "UCG", "AGU", "AGC"},
				{"ACU", "ACC", "ACA", "ACG"}, {"UGG"}, {"UAU, UAC"}, {"GUU", "GUC", "GUA", "GUG"}, {"UAA", "UGA", "UAG"}};
		String [] aa = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "M", "L", "K", "F", "P", "S", "T", "W", "Y", "V", "_STOP_"};
		
		//Transcription code initialization
		int index=0;
		for (String n : nucleotides){TRANSCRIPTION_CODE.put(n,com[index++]);COMPLEMENT.put (n, compNuc[index-1]);}
		
		//Translation code initialization
		index=0;
		for (String a : aa){ArrayList <String>arr = new ArrayList (); arr.addAll(Arrays.asList(codons[index++]));TRANSLATION_CODE.put(a,arr);}
	}
	public static final HashMap <String, String> TRANSCRIPTION_CODE = new HashMap ();
	public static final HashMap <String, String> COMPLEMENT = new HashMap ();
	public static final HashMap <String, ArrayList <String>> TRANSLATION_CODE = new HashMap ();
	
	public static String reverseComplement (String dna){
		String rc = "";
		for (int i= dna.length()-1; i >= 0; i--){
			rc += COMPLEMENT.get(""+dna.charAt(i));
		}
		return rc;
	}
	
	//Transcribe dna to RNA given a starting nucleotide, returns rna in 5'-3' orientation
	public static String transcribe (String dna, int start){
		String rna = "";
		for (int i= start-1; i < dna.length(); i++){rna+= TRANSCRIPTION_CODE.get(""+dna.charAt(i));}
		return reverse(rna);
	}

	private static String reverse (String str){
		String out = "";
		for (int i=str.length()-1; i >=0; i--){
			out+= str.charAt(i);
		}
		return out;
	}
	//Translate dna/rna to protein given a start point for ORF
	public static String translate (boolean isRNA, String input, int start){
		String rna=(!isRNA)?transcribe (input, start) : input.substring(start-1);
		String protein="";
		for (int i=0; i < rna.length(); i+=3){
			if (i+2 >= rna.length())return protein;
			String codon= rna.substring(i, i+3);
			for (String aa : TRANSLATION_CODE.keySet()){
				if (TRANSLATION_CODE.get(aa).contains(codon.toUpperCase())){protein+= aa;break;}
			}
		}
		return protein;
	}

	//Translate all ORFs
	public static ArrayList <String> translateAll (boolean isRNA, String input){
		String rna=(!isRNA)?transcribe (input, 1) : input;
		ArrayList <String> arr = new ArrayList ();
		for (int i=0; i <3; i++){
			arr.add(translate(true, rna.substring(i), 1));
		}
		return arr;
	}
	
	//mutate a nucleotide 
	public static String mutate (String input, int pos, char n){
		return input.substring(0, pos-1)+n+input.substring(pos);
	}
}

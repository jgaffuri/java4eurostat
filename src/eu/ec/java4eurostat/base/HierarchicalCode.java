/**
 * 
 */
package eu.ec.java4eurostat.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * @author julien Gaffuri
 *
 */
public class HierarchicalCode implements Comparable<HierarchicalCode> {
	public HierarchicalCode(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public String code = null, description = null;
	public ArrayList<HierarchicalCode>  fathers = new ArrayList<HierarchicalCode>();
	public ArrayList<HierarchicalCode> children = new ArrayList<HierarchicalCode>();


	public void printHierarchy() {
		printHierarchy(0);
	}
	private void printHierarchy(int indent) {
		for(int i=0; i<indent; i++) System.out.print("    ");
		System.out.println(code+"\t\t"+description);
		ArrayList<HierarchicalCode> childrenS = new ArrayList<HierarchicalCode>(children);
		Collections.sort(childrenS);
		for(HierarchicalCode child : childrenS)
			child.printHierarchy(indent+1);
	}

	public boolean isRoot() { return fathers.size() == 0; }
	public boolean isLeaf() { return children.size() == 0; }



	@Override
	public int compareTo(HierarchicalCode hc) {
		return code.compareTo(hc.code);
	}
	public void sort(){
		Collections.sort(this.children);
		for(HierarchicalCode hc : this.children) hc.sort();
	}

	public static void sort(List<? extends HierarchicalCode> hcs){
		Collections.sort(hcs, new Comparator<HierarchicalCode>() {
			@Override
			public int compare(HierarchicalCode hc1, HierarchicalCode hc2) { return hc1.compareTo(hc2); }
		});
		for(Object obj : hcs){
			HierarchicalCode hc = (HierarchicalCode)obj;
			hc.sort(); 
		}
	}

	@Override
	public String toString() {
		//return code+"\t"+description+":" + (fathers.size()==0?"ROOT":fathers.size()==1?fathers.get(0).code:fathers.size()) + ":"+children.size();
		return code;
	}
	public static void printHierarchy(Collection<?> hcs) {
		for(Object obj : hcs){
			HierarchicalCode hc = (HierarchicalCode)obj;
			if(hc.isRoot()) hc.printHierarchy(); 
		}
	}


	public HashSet<HierarchicalCode> getDescendants() {
		HashSet<HierarchicalCode> out = new HashSet<HierarchicalCode>();
		if(this.children == null || this.children.size()==0) return out;
		out.addAll(this.children);
		for(HierarchicalCode ch : this.children)
			out.addAll(ch.getDescendants());
		return out;
	}

	public HashSet<HierarchicalCode> getAncestors() {
		HashSet<HierarchicalCode> out = new HashSet<HierarchicalCode>();
		if(this.fathers == null || this.fathers.size()==0) return out;
		out.addAll(this.fathers);
		for(HierarchicalCode f : this.fathers)
			out.addAll(f.getAncestors());
		return out;
	}

	public boolean isAncestorOf(HierarchicalCode ds) {
		if(ds.fathers == null || ds.fathers.size() == 0) return false;
		if(ds.fathers.contains(this)) return true;
		for(HierarchicalCode f : ds.fathers) if(this.isAncestorOf(f)) return true;
		return false;
	}

	public Collection<HierarchicalCode> getSiblings() {
		HashSet<HierarchicalCode> out = new HashSet<HierarchicalCode>();
		if(this.fathers == null) return out;
		for(HierarchicalCode f : this.fathers)
			for(HierarchicalCode sib : f.children)
				if(sib!=this) out.add(sib);
		return out;
	}

	public interface FatherSelectionCriteria { boolean ignore(HierarchicalCode father);  }
	public static ArrayList<HierarchicalCode> reduce(Collection<HierarchicalCode> cs){ return reduce(cs); }
	public static ArrayList<HierarchicalCode> reduce(Collection<HierarchicalCode> cs, FatherSelectionCriteria fsc){
		ArrayList<HierarchicalCode> out = new ArrayList<HierarchicalCode>(cs);
		HierarchicalCode red = findReduction(out, fsc);
		while (red != null){
			out.removeAll(red.children);
			out.add(red);
			red = findReduction(out, fsc);
		}
		return out;
	}
	private static HierarchicalCode findReduction(Collection<HierarchicalCode> cs, FatherSelectionCriteria fsc) {
		//get all fathers
		HashSet<HierarchicalCode> fathers = new HashSet<HierarchicalCode>();
		for(HierarchicalCode c : cs) fathers.addAll(c.fathers);

		//check if all children of a father are in set
		for(HierarchicalCode father : fathers){
			if(fsc!=null && fsc.ignore(father)) continue;
			boolean allIn = true;
			for(HierarchicalCode child : father.children)
				if(!cs.contains(child)) { allIn=false; break; }
			if(allIn) return father;
		}
		return null;
	}





	/*
	@Override
	public boolean equals(Object o) {
		if(o instanceof HierarchicalCode) return code == ((HierarchicalCode)o).code;
		return super.equals(o);
	}
	 */
}

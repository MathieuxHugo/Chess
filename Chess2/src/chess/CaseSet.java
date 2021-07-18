package chess;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import interfaceGraphique.Case;

public class CaseSet implements Set<Case> {
	private int size;
	private Case tab[];
	public CaseSet() {
		this.size=0;
	}
	@Override
	public boolean add(Case e) {
		Case temp[];
		if(this.size==0) {
			tab=new Case[1];
			tab[0]=e;
		}
		else {
			if(!this.contains(e)) {
				temp=new Case[this.size+1];
			}
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Case> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Case> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

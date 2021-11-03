package Search.VanilaAlgorithms;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

//public class GenericState<StateRep, ExtraInfo> {
public class GenericState<R> {

	private final R curRep;	// data representetion
	private double cost;
	private GenericState<R> parentState;

	public GenericState(R newRep, double cost) {
		this.cost = cost;
		curRep = newRep;
		parentState = null;
	}
	public GenericState(R newRep, double cost, GenericState<R> parentState) {
		this.cost = cost;
		this.curRep = newRep;
		this.parentState = parentState;
	}

	public GenericState<R> getParentState() {
		return parentState;
	}
	public R getStateRepresentor() {
		return curRep;
	}
	public double getCost() {
		return cost;
	}

	public boolean betterThen(GenericState<R> anotherState) {
		return this.cost <= this.cost;		// can be improved with Comperator<T>
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Type thisRepType = extractParameters(this)[0],
				objectRepType = extractParameters(o)[0];
		if (!thisRepType.equals(objectRepType))	return false;

		GenericState<R> oState = (GenericState<R>) o;

		if (curRep != null ? !curRep.equals(oState.curRep) : oState.curRep != null) return false;
		return true;
	}
	private static Type[] extractParameters(Object obj) {
		return ((ParameterizedType) obj.getClass().getGenericSuperclass()).getActualTypeArguments();
	}

	public int hashCode() {
		return this.curRep.hashCode();
	}


//	public static class StateComperator<R> implements Comparator<GenericState<R>>
//	{
//		@Override
//		public int compare(GenericState<R> o1, GenericState<R> o2) {
//			if (o1.cost < o2.cost)          return -1;
//			else if (o1.cost == o2.cost)    return 0;
//			else                            return 1;
//		}
//	}


	public static <R> Comparator<GenericState<R>> getComperator(){
		return new Comparator<GenericState<R>>() {
			@Override
			public int compare(GenericState<R> o1, GenericState<R> o2) {
				if (o1.cost < o2.cost)          return -1;
				else if (o1.cost == o2.cost)    return 0;
				else                            return 1;
			}
		};
	}

}

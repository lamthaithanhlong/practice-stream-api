package pratice;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import pratice.TaxReturn.Type;

class TaxPayer{
	private int id;
	private String name;
	private List<TaxReturn> taxReturns;
	
	public TaxPayer(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TaxReturn> getTaxReturns() {
		return taxReturns;
	}

	public void setTaxReturns(List<TaxReturn> taxReturns) {
		this.taxReturns = taxReturns;
	}

	public TaxPayer(int id, String name, List<TaxReturn> taxReturns) {
		this.id = id;
		this.name = name;
		this.taxReturns = taxReturns;
	}
	
	@Override
	public String toString() {
		return "TaxPayer{" +
				"Name=" + getName() +
				", Id=" + getId() +
				"}";
	}
}


class TaxReturn {
	private int tid;
	private LocalDate submitDate;
	private Type type;
	
	public TaxReturn(int tid, LocalDate submitDate, Type type) {
		this.tid = tid;
		this.submitDate = submitDate;
		this.type = type;
	}
	
	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public LocalDate getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDate submitDate) {
		this.submitDate = submitDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	enum Type {
		_1040, _1040A("A"), _1040EZ("EZ"), _1040NR("NR");
		
		private String abbrevation;
		private Type() {
			
		}
		private Type(String abbrevation) {
			this.abbrevation = abbrevation;
		}
		private String getAbbrevation() {
			return this.abbrevation;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tid;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxReturn other = (TaxReturn) obj;
		if(tid != other.tid)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TaxReturn{" +
				"submitDate=" + submitDate +
				", tid=" + tid +
				"}";
	}
}
class TaxPayerNotFoundException extends Exception {
	private static final long serialVersionUID = 6124649126501534500L;
	
	private String message;
	public TaxPayerNotFoundException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
//class Util {
//	public static TaxPayer getTaxPayerByName(String name) throws TaxPayerNotFoundException {
//			throw new TaxPayerNotFoundException("No TaxPayer by this name: " + name);
//		}
//	}
class Util {
    public static TaxPayer getTaxPayerByName(String name) throws  TaxPayerNotFoundException{

        throw new TaxPayerNotFoundException("No TaxPayer found by this name" + name);
    }
    public  static TaxPayer uncheckedgetTaxPayerByName(String name){
        try{
            return Util.getTaxPayerByName(name);
        }catch (TaxPayerNotFoundException e){
            throw  new RuntimeException(e);
        }
    }
}
@FunctionalInterface
interface ThrowingFunction<T, R> {
    R apply(T t) throws RuntimeException;
}

class ExceptionUtils {
    static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                return null;
            }
        };
    }
}

public class main5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaxReturn r1 = new TaxReturn(1001, LocalDate.of(2013, 02, 03), Type._1040NR);
		TaxReturn r2 = new TaxReturn(1002, LocalDate.of(2015, 02, 03), Type._1040A);
		TaxReturn r3 = new TaxReturn(1003, LocalDate.of(2013, 02, 03), Type._1040NR);
		
		TaxReturn r4 = new TaxReturn(2001, LocalDate.of(2013, 02, 03), Type._1040NR);
		TaxReturn r5 = new TaxReturn(2002, LocalDate.of(2015, 02, 03), Type._1040NR);
		TaxReturn r6 = new TaxReturn(2003, LocalDate.of(2016, 02, 03), Type._1040);
		TaxReturn r7 = new TaxReturn(2004, LocalDate.of(2016, 02, 03), Type._1040A);
		
		TaxPayer payer1 = new TaxPayer(99991, "Tom", Arrays.asList(r1,r2,r3));
		TaxPayer payer2 = new TaxPayer(99992, "Josh", Arrays.asList(r4,r5,r6));
		TaxPayer payer3 = new TaxPayer(99993, "William", Arrays.asList(r1,r3));
		TaxPayer payer4 = new TaxPayer(99993, "Joshwa", Arrays.asList(r4,r5,r6,r1));
		TaxPayer payer5 = new TaxPayer(99994, "Linda", Arrays.asList(r2,r6,r7));
		TaxPayer payer6 = new TaxPayer(99995, "Karen", Arrays.asList(r2,r6,r7));
		
		List<TaxPayer> taxpayers = Arrays.asList(payer1, payer2, payer3, payer4, payer5,  payer6);
		
		
		// Find the name of all TaxPayers who have submitted a return of type _1040NR
		List<String> names = taxpayers.stream().filter(x->x.getTaxReturns().stream().anyMatch(r->r.getType()==TaxReturn.Type._1040NR)).map(TaxPayer::getName).collect(Collectors.toList());
		System.out.println("names"+names);
		
		// Return the number of Tax Payer
		long NumOfTaxPayer = taxpayers.stream().count();
		System.out.println(NumOfTaxPayer);
		
		// Return the number of tax Payer with type 1040NR
		long NumOfTaxPayer1040NR = taxpayers.stream().filter(x->x.getTaxReturns().stream().anyMatch(r->r.getType()==TaxReturn.Type._1040NR)).map(TaxPayer::getName).count();
		System.out.println(NumOfTaxPayer1040NR);
		
		// Get the list tax Payer with 1040NR
		Map<String, List<TaxPayer>> ListOfTaxPayer = taxpayers.stream().filter(x->x.getTaxReturns().stream().anyMatch(r->r.getType()==TaxReturn.Type._1040NR)).collect(Collectors.groupingBy(TaxPayer::getName));
		System.out.println(ListOfTaxPayer);
		
		// Get tax payer by name
		Map<String,Long> getTaxPayerByName = taxpayers.stream().collect(Collectors.groupingBy(TaxPayer::getName,Collectors.counting()));
		System.out.println(getTaxPayerByName);
		
//		Create a stream pipeline that, when run, finds a list of TaxReturn for which submitDate’s year is 2016, Type is 1040A and sort them by tid.
				List<TaxReturn> result = taxpayers.stream()
						.flatMap(x->x.getTaxReturns().stream())
						.filter(x1->x1.getSubmitDate().getYear()==2016)
						.filter(x2->x2.getType().equals(Type._1040A))
						.sorted(Comparator.comparing(TaxReturn::getTid))
						.collect(Collectors.toList());
		System.out.println(result);
		result.stream().map(TaxReturn::getTid).forEach(System.out::println);
		
		List<TaxPayer> result1 = taxpayers.stream()
			.filter(x->x.getTaxReturns().stream().anyMatch(x1->x1.getSubmitDate().getYear()==2016))
			.filter(x2->x2.getTaxReturns().stream().anyMatch(x3->x3.getType().equals(Type._1040A)))
			.collect(Collectors.toList());
		result1.stream().flatMap(x->x.getTaxReturns().stream()).sorted(Comparator.comparing(TaxReturn::getTid)).forEach(x->System.out.println(x.getTid()));
		
//		Create a stream pipeline that, when run, finds a list of distinct TaxReturn for which Type’s abbrevation is "NR". 
		List<TaxReturn> result2 = taxpayers.stream().flatMap(x->x.getTaxReturns().stream())
				.filter(x->x.getType().name().contains("NR"))
				.distinct()
				.collect(Collectors.toList());
		System.out.println("print");
		result2.stream().map(x->x.getTid()).forEach(System.out::println);
		
		
//		Create a stream pipeline that, when run, returns a String of all
//		TaxPayers’ names for which name’s length is greater than 5, sorted alphabetically
		String result3 = taxpayers.stream()
				.filter(x->x.getName().length()>5)
				.map(TaxPayer::getName)
				.sorted()
				.collect(Collectors.joining(" ,"));
		System.out.println(result3);
		
//		Create a stream pipeline that, when run, determines whether there is any
//		TaxPayer who only submitted 2 TaxReturns? No Credit if you use count() or
//		list.size() to check.
		System.out.println("result4");
		Optional<TaxPayer> result4 = taxpayers.parallelStream()
				.filter(x->x.getTaxReturns().size()>2)
				.findAny();
		
		System.out.println(result4.isPresent());
		
//		Create a stream pipeline that, when run, returns a list of TaxPayer by using
//		the names list below. Use the getTaxPayerByName() in Util class
//		List<String> names = Arrays.asList("Olive", "Jackson",
//		"Anna");
		List<String> names1 = Arrays.asList("Olive","Jackson","Anna");
		List<TaxPayer> result5 = names1.stream().map(name1->{
			try {
				return Util.getTaxPayerByName(name1);
			} catch (TaxPayerNotFoundException e) {
				System.out.println(e.getMessage());
			}
			return null;
		}).collect(Collectors.toList());
		
		// another way without try/catch
		List<String> names2 = Arrays.asList("Olive","Jackson","Anna");
//		List<TaxPayer> result6 = names.stream()
//		  .map(name -> Util.getTaxPayerByName(name))
//		  .filter(Objects::nonNull)
//		  .collect(Collectors.toList());
//		System.out.println(result5);
		
		// another way without try/catch
//		List<TaxPayer> result10 = names1.stream()
//				  .map(ExceptionUtils.unchecked(name1 -> Util.getTaxPayerByName(name1)))
//				  .filter(Objects::nonNull)
//				  .collect(Collectors.toList());

		
//		Find all TaxPayer objects whose name starts with "Jo"
		List<TaxPayer> result6 = taxpayers.stream().filter(x->x.getName().startsWith("Jo")).collect(Collectors.toList());
		System.out.println(result6.stream().map(t->t.getName()).collect(Collectors.joining(" ,")));
		
//		Find all TaxReturns submitted in the year 2016
		List<TaxReturn> result7 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.filter(x->x.getSubmitDate().getYear()==2016)
				.collect(Collectors.toList());
		System.out.println(result7);
		
//		Find the average submission date of all TaxReturns
		Double result8 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.mapToLong(y->y.getSubmitDate().toEpochDay())
				.average()
				.orElse(0);
		System.out.println(result8);
		
		LongStream result9 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.mapToLong(y->y.getSubmitDate().toEpochDay())
				.peek(System.out::println);
		System.out.println(result9);
		
//		Find the tax returns of the TaxPayer with the maximum number of returns
		Optional<TaxPayer> result11 = taxpayers.stream()
				.max(Comparator.comparingInt(t->t.getTaxReturns().size()));
		
		List<TaxReturn> taxReturnsOfMaxTaxPayer = result11
				.map(TaxPayer::getTaxReturns)
				.orElse(Collections.emptyList());
		System.out.println(taxReturnsOfMaxTaxPayer);
		
		
//		Group TaxReturns by their submission year
		Map<Object, List<TaxReturn>> result12 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.collect(Collectors.groupingBy(x->x.getSubmitDate().getYear()));
		
		System.out.println(result12);
		
//		List all TaxReturns submitted by a TaxPayer whose name is "Josh".
		List<TaxReturn> result13 = taxpayers.stream()
				.filter(x->x.getName().contains("Josh"))
				.flatMap(x->x.getTaxReturns().stream())
				.distinct()
				.collect(Collectors.toList());
		System.out.println(result13);
		
//		List all TaxPayers who have submitted a TaxReturn of type _1040NR.
		List<TaxPayer> result14 = taxpayers.stream()
				.filter(x->x.getTaxReturns().stream().anyMatch(x1->x1.getType()==Type._1040NR))
				.collect(Collectors.toList());
		System.out.println(result14.stream().map(x->x.getName()).collect(Collectors.toList()));
		
//		Find the TaxReturn submitted by a TaxPayer with id 99991.
		List<TaxReturn> result15 = taxpayers.stream()
				.filter(x->x.getId()==99991)
				.flatMap(x->x.getTaxReturns().stream())
				.collect(Collectors.toList());
		System.out.println(result15.stream().map(x->x.getTid()).collect(Collectors.toList()));
		
//		Group all TaxReturns by the TaxReturn Type.
		Map<Type, List<TaxReturn>> result16 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.collect(Collectors.groupingBy(TaxReturn::getType));
		System.out.println(result16);
		
//		Write a Java stream that filters the taxpayers list and returns only those taxpayers who have filed the _1040NR type tax returns.
		List<TaxPayer> result17 = taxpayers.stream()
				.filter(taxpayer->taxpayer.getTaxReturns().stream().anyMatch(x->x.getType().equals(Type._1040NR)))
				.collect(Collectors.toList());
		System.out.println(result17.stream().map(x->x.getName()).collect(Collectors.toList()));
		
//		Write a Java stream that maps the taxpayers list to a list of submitDate for each _1040NR type tax return.
		List<LocalDate> result18 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.filter(x->x.getType().equals(Type._1040NR))
				.map(TaxReturn::getSubmitDate)
				.collect(Collectors.toList());
		System.out.println(result18);
		
//		Write a Java stream that groups the taxpayers list by the number of _1040NR type tax returns they have filed.
		Map<Integer, List<TaxPayer>> groupedTaxpayers = taxpayers.stream()
                .collect(Collectors.groupingBy(taxPayer -> (int) taxPayer.getTaxReturns().stream().filter(taxReturn -> taxReturn.getType().equals(Type._1040NR)).count()));

		System.out.println(groupedTaxpayers);
		
//		Write a Java stream that finds the maximum submit date for each _1040NR type tax return among all taxpayers.
		Optional<LocalDate> result19 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.map(x->x.getSubmitDate())
				.max(Comparator.naturalOrder());
		System.out.println(result19);
		
//		Find the TaxPayer objects with name 'Josh':
		List<TaxPayer> result20 = taxpayers.stream()
				.filter(x->x.getName().contains("Josh"))
				.collect(Collectors.toList());
		System.out.println(result20);
		
//		Find the number of TaxReturns submitted in 2016:
		Long result21 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.filter(x->x.getSubmitDate().getYear()==2016)
				.count();
		System.out.println(result21);
		
//		Find the average year of submission of all TaxReturns:
		Long result22 = (long) taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.mapToInt(x->x.getSubmitDate().getYear())
				.average().orElse(0);
		
		System.out.println(result22);
		
//		Find the TaxReturn with the earliest submit date:
		Integer result23 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.map(x->x.getSubmitDate().getYear())
				.sorted(Comparator.naturalOrder())
				.findFirst().orElse(null);
		System.out.println(result23);
		
		LocalDate result24 = taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.min(Comparator.comparing(TaxReturn::getSubmitDate))
				.map(TaxReturn::getSubmitDate)
				.orElse(null);
		System.out.println(result24);
		
//		List<TaxPayer> taxPayersByName = names.stream()
//                .map(name -> Util.uncheckedgetTaxPayerByName(name))
//                .collect(Collectors.toList());
//		System.out.println(taxPayersByName);
		
//		Find all the TaxReturns submitted in 2013 and of Type _1040NR:
		List<TaxReturn> result25 = (List<TaxReturn>) taxpayers.stream()
				.flatMap(x->x.getTaxReturns().stream())
				.filter(x->x.getSubmitDate().getYear()==2013)
				.filter(x->x.getType().equals(Type._1040NR))
				.collect(Collectors.toList());
		System.out.println(result25);
	}
}



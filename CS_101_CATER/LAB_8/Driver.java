class Driver{
	public static void main(String[] args){
		Day a = new Day();
		Day b = new Day(a);
		Day c = new Day(2000, 11, 10);
		Day d = new Day(1990, 6);
		Day e = new Day(1850);
		
		//System.out.println(a.classId());

		
		//print initial values
		System.out.println("Day1 zero constructor: " + a);
		System.out.println("Day2 copy constructor: " + b);
		System.out.println("Day3 yyyy,mm,dd constructor: " + c);
		System.out.println("Day4 yyyy,mm constructor: " + d);
		System.out.println("Day5 yyyy constructor: " + e);
		System.out.println();
		
		//print day of week
		System.out.println("Day1 Today is a: " + a.getDayOfWeek());
		System.out.println("Day2 (today): " + b.getDayOfWeek());
		System.out.println("Day3: " + c.getDayOfWeek());
		System.out.println("Day4: " + d.getDayOfWeek());
		System.out.println("Day5: " + e.getDayOfWeek());
		System.out.println();
		
		//print years
		System.out.println("Day1 year: " + a.getYear());
		System.out.println("Day2 year: " + b.getYear());
		System.out.println("Day3 year: " + c.getYear());
		System.out.println("Day4 year: " + d.getYear());
		System.out.println("Day5 year: " + e.getYear());
		System.out.println();
		
		//print months
		System.out.println("Day1 month: " + a.getMonth());
		System.out.println("Day2 month: " + b.getMonth());
		System.out.println("Day3 month: " + c.getMonth());
		System.out.println("Day4 month: " + d.getMonth());
		System.out.println("Day5 month: " + e.getMonth());
		System.out.println();
		
		//print days
		System.out.println("Day1 day: " + a.getDay());
		System.out.println("Day2 day: " + b.getDay());
		System.out.println("Day3 day: " + c.getDay());
		System.out.println("Day4 day: " + d.getDay());
		System.out.println("Day5 day: " + e.getDay());
		System.out.println();
		
		a.setYear(2005);
		System.out.println("Day1 year=2005?: " + a.getYear());
		System.out.println();
		
		b.setMonth(12);
		System.out.println("Day2 month=12?: " + b.getMonth());
		System.out.println();
		
		c.setDay(30);
		System.out.println("Day3 day=30?: " + c.getDay());
		System.out.println();
		
		c.nextDay();
		System.out.println("Day3 next day?: " + c.getDay());
		System.out.println();
		
		c.previousDay();
		System.out.println("Day3 previous day?: " + c.getDay());
		System.out.println("Day3 month(odd 31, even 30): " + c.getMonth());
		System.out.println();
		
		Day f = e.addDays(50);
		System.out.println("Day5= " + e);
		System.out.println("Day6 (Day5+50)?= " + f);
		System.out.println();
		
		System.out.println("Days Between Day5 and 6: " + f.daysBetween(e));
		System.out.println();
		
		System.out.println("Day6 before Day1?: " + e.before(a));
		
		System.out.println("Day6 after Day1?: " + e.after(a));
		
		System.out.println("Day2=Day3?: " + b.equals(c));
		Day g = new Day(d);
		System.out.println("Day7=Day4? (true): " + g.equals(d));
		System.out.println();
		
		
	}
}
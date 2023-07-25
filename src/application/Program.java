package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.next();
		
		System.out.print("Enter salary: ");
		double salaryToCompare = sc.nextDouble();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			
			List<Employee> list = new ArrayList<Employee>();
			
			while (line != null) {
				String[] fields = line.split(",");
				String name = fields[0];
				String email = fields[1];
				double salary = Double.parseDouble(fields[2]);
				
				list.add(new Employee(name, email, salary));
				
				line = br.readLine();
			}
			
			Collator collator = Collator.getInstance(new Locale("pt", "BR"));
			collator.setStrength(Collator.TERTIARY);
			
			List<String> eMails = list.stream()
					.filter(emp -> emp.getSalary() > salaryToCompare)
					.map(Employee::getEmail)
					.sorted(collator) // Como tudo está em letras minúsculas eu poderia usar somente o método .sorted() sem usar o collator
					.collect(Collectors.toList());
			
			System.out.println("Email of people whose salary is more than 2000.00:");
			eMails.forEach(System.out::println);
			
			Double salaries = list.stream()
					.filter(emp -> emp.getName().charAt(0) == 'M')
					.map(Employee::getSalary)
					.reduce(0.0, (emp1, emp2) -> emp1 + emp2);
			
			System.out.println("Sum of salary of people whose name starts with 'M': "
					+ String.format("%.2f", salaries));
						
		} catch (IOException e) {
			e.getStackTrace();
		}
		
		sc.close();
	}
}

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.round;

/**
 * 0.1. Посмотреть разные статьи на Хабр.ру про Stream API
 * 0.2. Посмотреть видеоролики на YouTube.com Тагира Валеева про Stream API
 * <p>
 * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
 * 1.1 Найти максимальное
 * 1.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
 * 1.3 Найти количество чисел, квадрат которых меньше, чем 100_000
 * <p>
 * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
 * 2.1 Создать список из 10-20 сотрудников
 * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
 * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
 * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
 * 2.5 * Из списка сотрудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
 */

public class Main {
    public static void main(String[] args) {

        // 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
        List<Long> list = Stream.generate(() -> ThreadLocalRandom.current().nextLong(1, 1_000_000))
                .limit(1_000)
                .collect(Collectors.toCollection(ArrayList::new));

        // 1.1 Найти максимальное значение
        System.out.println("Максимально число равно: " + list.stream().max((l1, l2) -> Math.toIntExact(l1 - l2)).get());

        // 1.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
        long sum = list.stream()
                .filter(i -> i > 500_000)
                .map(i -> i * 5 - 150)
                .mapToLong(Long::longValue).sum();
        System.out.println("Результат равен: " + sum);

        // 1.3 Найти количество чисел, квадрат которых меньше, чем 100_000
        long count = list.stream()
                .filter(i -> (i * i < 100_000))
                .count();
        System.out.println("Количество чисел, квадрат которых меньше, чем 100_000: " + count);

        // 2.1 Создать список из 10-20 сотрудников
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(
                new Employee("Иван", 20, 5_000, "Технический отдел"),
                new Employee("Мария", 43, 30_000, "Технический отдел"),
                new Employee("Илья", 34, 45_000, "Технический отдел"),
                new Employee("Денис", 26, 38_000, "Финансовый отдел"),
                new Employee("Василий", 38, 39_000, "Финансовый отдел"),
                new Employee("Ольга", 27, 49_000, "Финансовый отдел"),
                new Employee("Данил", 19, 55_000, "Финансовый отдел"),
                new Employee("Артем", 42, 8_000, "Отдел кадров"),
                new Employee("Григорий", 56, 70_000, "Отдел кадров"),
                new Employee("Валентина", 44, 42_000, "Отдел кадров"))
        );

        // 2.2 Вывести список всех различных отделов (department) по списку сотрудников
        Set<String> departments = employeeList.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(departments);

        // 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
        employeeList.stream()
                .filter(employee -> employee.getSalary() < 10_000)
                .forEach(employee -> employee.setSalary(employee.getSalary() * 1.2));

        // 2.4 Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела

        Map<String, List<Employee>> employeesByDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println(employeesByDepartment);

        // 2.5 Из списка сотрудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
        Map<String, Double> averageSalaryByDepartment = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)));
        for (Map.Entry<String, Double> item : averageSalaryByDepartment.entrySet()) {
            System.out.println("Средняя зарплата подразделения \"" + item.getKey() + "\" составляет " + Math.round(item.getValue()) + " руб.");
        }
    }
}

// 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
class Employee {
    String name;
    int age;
    double salary;
    String department;

    public Employee(String name, int age, double salary, String department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
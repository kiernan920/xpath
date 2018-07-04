import javax.xml.xpath.XPathConstants;
import java.util.List;

public class Main {

    private static XpathExpressionResolver xpathExpressionResolver  = new XpathExpressionResolver("employees.xml");;

    public static void main(String[] args) {
        printAllFemaleEmployees();
        printEmployeeById(1);
        printEmployeeAgeGreaterThan(10);
    }

    //Get all the Employee elements where gender element is female
    private static void printAllFemaleEmployees(){
        List<String> femaleEmployees = (List<String>) xpathExpressionResolver.resolve("//Employees/Employee[gender='Female']/name/text()", XPathConstants.NODESET);
        System.out.println("All Female Employees: " + femaleEmployees);
    }

    //Get all the Employee elements where the id attribute value is equal to <someValue>
    private static void printEmployeeById(int employeeId){
        String employee = (String) xpathExpressionResolver.resolve("/Employees/Employee[@id='" + employeeId + "']/name/text()", XPathConstants.STRING);
        System.out.println("All Employees with employId no." + employeeId + " are " + employee);
    }

    //Get all the employees where the age element is greater than <someValue>
    private static void printEmployeeAgeGreaterThan(int employeeAge){
        List<String> employees = (List<String>) xpathExpressionResolver.resolve("/Employees/Employee[age>" + employeeAge + "]/name/text()", XPathConstants.NODESET);
        System.out.println("All Employees over " + employeeAge + " are "  + employees);
    }
}
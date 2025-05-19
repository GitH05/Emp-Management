package com.employee.Emp_Management.controller;

import com.employee.Emp_Management.entity.EmployeeEntity;
import com.employee.Emp_Management.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmployeeService employeeService;

    // Home page - list all employees and show session message if present
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Add all employee
        List<EmployeeEntity> employees = employeeService.getAllEmployee();
        model.addAttribute("employees", employees);

        // Display session message if present
        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }

        return "index"; // Thymeleaf will resolve to templates/index.html
    }

    // Form page to add new employee
    @GetMapping("/addEmployee")
    public String addEmployee() {
        return "addEmp"; // resolves to templates/addEmp.html
    }

    // Save employee and redirect with session message
    @PostMapping("/register")
    public String employeeRegister(@ModelAttribute EmployeeEntity employee, HttpSession session) {
        employeeService.addEmployee(employee);
        session.setAttribute("msg", "Employee added successfully.");
        return "redirect:/";
    }

//    get edit form
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        EmployeeEntity e=employeeService.getEmployeeById(id);
        model.addAttribute("emp", e);
        return "edit";
    }

//    update
    @PostMapping("/update")
    public String update(@ModelAttribute EmployeeEntity employee, HttpSession session) {
        employeeService.addEmployee(employee);
        session.setAttribute("msg","Update Successfully.");
        return "redirect:/";
    }

//    delete
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id, HttpSession session) {
        employeeService.deleteEmployee(id);
        session.setAttribute("msg","Delete Successfully.");
        return "redirect:/";
    }
}
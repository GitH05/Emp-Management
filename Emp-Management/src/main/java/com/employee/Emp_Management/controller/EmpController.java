package com.employee.Emp_Management.controller;

import com.employee.Emp_Management.entity.EmployeeEntity;
import com.employee.Emp_Management.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Home page - accessible to all authenticated users
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<EmployeeEntity> employees = employeeService.getAllEmployee();
        model.addAttribute("employees", employees);

        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "index";
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";  // your login.html
    }

    // Form page to add new employee (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addEmployee")
    public String addEmployee() {
        return "addEmp"; // templates/addEmp.html
    }

    // Save employee (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String employeeRegister(@ModelAttribute EmployeeEntity employee, HttpSession session) {
        employeeService.addEmployee(employee);
        session.setAttribute("msg", "Employee added successfully.");
        return "redirect:/";
    }

    // Get edit form (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        EmployeeEntity e = employeeService.getEmployeeById(id);
        model.addAttribute("emp", e);
        return "edit";
    }

    // Update employee (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String update(@ModelAttribute EmployeeEntity employee, HttpSession session) {
        employeeService.addEmployee(employee);
        session.setAttribute("msg", "Update Successfully.");
        return "redirect:/";
    }

    // Delete employee (ADMIN only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id, HttpSession session) {
        employeeService.deleteEmployee(id);
        session.setAttribute("msg", "Delete Successfully.");
        return "redirect:/";
    }

    // Error page for access denied
    @GetMapping("/error")
    public String accessDenied() {
        return "error"; 
    }

}

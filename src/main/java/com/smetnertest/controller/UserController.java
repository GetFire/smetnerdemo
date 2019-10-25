package com.smetnertest.controller;

import com.smetnertest.dto.DtoUser;
import com.smetnertest.model.ListWrapper;
import com.smetnertest.service.ContactService;
import com.smetnertest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsCsvView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsHtmlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A REST controller to handle all {@link com.smetnertest.model.User} related
 * requests: get all users, edit user profile
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private ContactService contactService;
    private ApplicationContext applicationContext;


    @Autowired
    public UserController(UserService userService, ContactService contactService, ApplicationContext applicationContext) {
        this.userService = userService;
        this.contactService = contactService;
        this.applicationContext = applicationContext;
    }

    @GetMapping
    public ResponseEntity<List<DtoUser>> getAllUser() {
        List<DtoUser> result = userService.getAllUsers();
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoUser> updateUserContact(@PathVariable("id") long id,
                                                     @Valid @RequestBody DtoUser dtoUser) {
        DtoUser user = userService.findOne(id);
        if (user != null && id == user.getId()) {
            contactService.updateContact(dtoUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PutMapping("/all")
    public ResponseEntity<List<DtoUser>> updateAllUsers(@Valid @RequestBody ListWrapper dtoUsers) {
        List<DtoUser> currentUsers = userService.getAllUsers();
        if (currentUsers.size() == dtoUsers.getUsers().size()) {
            dtoUsers.getUsers().forEach(contactService::updateContact);
            return new ResponseEntity<List<DtoUser>>(userService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<List<DtoUser>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUser> getUserById(@PathVariable("id") long id) {
        DtoUser user = userService.findOne(id);
        if (user != null && user.getId() == id) {
            return new ResponseEntity<DtoUser>(user, HttpStatus.OK);
        }
        return new ResponseEntity<DtoUser>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/report/pdf")
    public ModelAndView reportPdf() {
        JasperReportsPdfView pdfReport = new JasperReportsPdfView();
        pdfReport.setUrl("classpath:users1.jrxml");
        pdfReport.setApplicationContext(applicationContext);
        Map<String, Object> params = new HashMap<>();
        params.put("datasource", userService.getAllUsers());
        return new ModelAndView(pdfReport, params);
    }

    @GetMapping("/report/html")
    public ModelAndView reportHtml() {
        JasperReportsHtmlView htmlReport = new JasperReportsHtmlView();
        htmlReport.setUrl("classpath:users1.jrxml");
        htmlReport.setApplicationContext(applicationContext);
        Map<String, Object> params = new HashMap<>();
        params.put("datasource", userService.getAllUsers());
        return new ModelAndView(htmlReport, params);
    }

    @GetMapping("/report/xls")
    public ModelAndView reportXml() {
        JasperReportsXlsView xlsReport = new JasperReportsXlsView();
        xlsReport.setUrl("classpath:users.jrxml");
        xlsReport.setApplicationContext(applicationContext);
        Map<String, Object> params = new HashMap<>();
        params.put("datasource", userService.getAllUsers());
        return new ModelAndView(xlsReport, params);
    }

    @GetMapping("/report/csv")
    public ModelAndView reportCsv() {
        JasperReportsCsvView csvReport = new JasperReportsCsvView();
        csvReport.setUrl("classpath:users.jrxml");
        csvReport.setApplicationContext(applicationContext);
        Map<String, Object> params = new HashMap<>();
        params.put("datasource", userService.getAllUsers());
        return new ModelAndView(csvReport, params);
    }


}

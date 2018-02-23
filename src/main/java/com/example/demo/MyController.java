package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private static final Logger LOG = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private MyService service;

    @PostMapping("/hello")
    public Response helloWorld(@RequestBody ContactInfo req) {
        LOG.info("Print Hello World {}", req);

        Response response = new Response();
        response.setMessage("Hello World " + req.getName());

        return response;
    }

    @GetMapping("/phonebook")
    public List<ContactInfo> select() {
        return service.select();
    }

    @PostMapping("/phonebook")
    public Response insert(@RequestBody ContactInfo req) {
        return service.insert(req);
    }

    @PutMapping("/phonebook/{name}")
    public Response update(@PathVariable String name, @RequestBody ContactInfo req) {
        return service.update(name, req);
    }

    @DeleteMapping("/phonebook/{name}")
    public Response delete(@PathVariable String name) {
        return service.delete(name);
    }
}

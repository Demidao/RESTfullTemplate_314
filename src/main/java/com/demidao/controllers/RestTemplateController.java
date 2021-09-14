package com.demidao.controllers;

import com.demidao.models.User;
import com.demidao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("")
public class RestTemplateController {

    private RestTemplate rt;
    private UserService service;
    private HttpHeaders headers;
    private String SESSION_ID;

    @Value("${api.host.baseurl}")
    private String baseUrl;

    @Value("${api.host.get_user}")
    private String _get;

    @Value("${api.host.baseurl.post_user}")
    private String _post;

    @Value("${api.host.baseurl.put_user}")
    private String _put;

    @Value("${api.host.baseurl.delete_user}")
    private String _delete;

    @Autowired
    public RestTemplateController(RestTemplate rt, UserService userService, HttpHeaders headers) {
        this.rt = rt;
        this.service = userService;
        this.headers = headers;
    }

    @RequestMapping("/retrieve-users")
    public ResponseEntity<User[]> getUsers() {
        ResponseEntity<User[]> response = rt.getForEntity(baseUrl + _get, User[].class);

        SESSION_ID = response.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", SESSION_ID);

        service.save(response.getBody());
        return response;
    }

    @PostMapping("/create-user")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody User user) {
        User savedUser = service.save(user);
        HttpEntity<User> entity = new HttpEntity<>(savedUser, headers);
        ResponseEntity<String> resp = rt.exchange(baseUrl + _post, HttpMethod.POST, entity, String.class);

        return new ResponseEntity<>(resp.getBody(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-user")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody User user) {
        User updatedUser = service.update(user);
        HttpEntity<User> entity = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> resp = rt.exchange(baseUrl + _put, HttpMethod.PUT, entity, String.class);

        return updatedUser != null
                ? new ResponseEntity<>(resp.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        User userTobeDeleted = service.delete(id);
        HttpEntity<User> entity = new HttpEntity<>(userTobeDeleted, headers);
        ResponseEntity<String> resp = rt.exchange(baseUrl + _delete + id, HttpMethod.DELETE, entity, String.class);

        return userTobeDeleted != null
                ? new ResponseEntity<>(resp.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

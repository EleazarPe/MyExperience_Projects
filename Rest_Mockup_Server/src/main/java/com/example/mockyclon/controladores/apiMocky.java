package com.example.mockyclon.controladores;

import com.example.mockyclon.encapsulaciones.Mockup;
import com.example.mockyclon.services.JwtServices;
import com.example.mockyclon.services.MockyServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class apiMocky {
    @Autowired
    MockyServices mockS;
    @Autowired
    JwtServices jwts;
    @RequestMapping("/{name}/{codigo}")
    public ResponseEntity<String> consulta(@PathVariable() String name, @PathVariable() String codigo,@RequestParam("token") String token){
        System.out.println("ENtra a esta parte por lo menos...");
        Mockup omock = mockS.buscarCodigoAndNombre(name,codigo);
        DateTimeFormatter nt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println(LocalDateTime.now().format(nt)+" otra: "+omock.getExp().format(nt));
        if(omock.isSeguridad()) {
           if(!jwts.generateJwt(omock).equals(token)){
               HttpHeaders headers = new HttpHeaders();
               headers.add("Location", "/mockycrud/");
               return new ResponseEntity<>(headers, HttpStatus.FOUND);
           }
        }
           if ((LocalDateTime.now()).isBefore(omock.getExp())) {
               MediaType temp = null;
               if (omock.getContent().equals("text/plain")) {
                   temp = MediaType.TEXT_PLAIN;
               } else if (omock.getContent().equals("text/html")) {
                   temp = MediaType.TEXT_HTML;
               } else if (omock.getContent().equals("application/json")) {
                   temp = MediaType.APPLICATION_JSON;
               } else if (omock.getContent().equals("application/xml")) {
                   temp = MediaType.APPLICATION_XML;
               }

               HttpHeaders headers = new HttpHeaders();
               if (!omock.getHeaders().isEmpty()) {
                   JSONObject headersJson = new JSONObject(omock.getHeaders());
                   for (String key : headersJson.keySet()) {
                       headers.add(key, headersJson.getString(key));
                   }
               }

               try {
                   Thread.sleep(omock.getDemora() * 1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               // System.out.println(new JSONObject(omock.getBody()));
               if (omock.getBody().isEmpty()) {
                   return ResponseEntity.status(Integer.parseInt(omock.getStatus())).contentType(temp).headers(headers).body("{}");
               } else {
                   return ResponseEntity.status(Integer.parseInt(omock.getStatus())).contentType(temp).headers(headers).body(omock.getBody());
               }

           } else {
               HttpHeaders headers = new HttpHeaders();
               headers.add("Location", "/mockycrud/");
               return new ResponseEntity<>(headers, HttpStatus.FOUND);
           }


    }

}

package com.example.mockyclon.controladores;

import com.example.mockyclon.encapsulaciones.sercurity.Rol;
import com.example.mockyclon.encapsulaciones.sercurity.Usuario;
import com.example.mockyclon.repositorio.security.RolRepository;
import com.example.mockyclon.services.SecurityService;
import com.example.mockyclon.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class crudUsuario {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    SecurityService securityService;
    @Autowired
    RolRepository rolRepository;

    @GetMapping("/lista")
    public String listado(Model model){

        Usuario user = securityService.getAuthorizedUser();
        if(user == null){
            return "redirect:/login";
        }

        if (!user.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN")){
            System.out.println("Not allowed");
            return "redirect:/usuario/lista/" + user.getUsername();
        }

        model.addAttribute("titulo", "CRUD Usuario");
        model.addAttribute("lista", usuarioService.findAll());
        return "/thymeleaf/crudUsuario/listarUsuario";
    }

    @GetMapping("/lista/{username}")
    public String verUsuario(Model model, @PathVariable(name = "username") String username){

        Usuario usuario = securityService.getAuthorizedUser();
        Usuario usuarioListado = usuarioService.findByUsername(username);

        if (!(usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN") || usuario.getUsername().equalsIgnoreCase(username))){
            return "redirect:/usuario/lista/"+usuario.getUsername();
        }

        model.addAttribute("titulo", "Ver Usuario");
        model.addAttribute("visualizar", "true");
        model.addAttribute("user", usuarioListado);

        return "thymeleaf/crudUsuario/verUsuario";
    }

    @GetMapping("/crear")
    public String crearUsuario(Model model){

        model.addAttribute("titulo", "Crear Usuario");
        model.addAttribute("visualizar", "false");
        model.addAttribute("tipo", "crear");
        model.addAttribute("accion","/usuario/crearPost");

        return "thymeleaf/crudUsuario/crearUsuario";
    }

    @PostMapping("/crearPost")
    public String crearPost(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nombre") String nombre,
                            @RequestParam(name = "admin", required = false) String admin)
    {

        Usuario usuario = securityService.getAuthorizedUser();
        if (usuario == null){
            return "redirect:/";
        }

        if (!usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN")){
            return "redirect:/usuario/" + usuario.getUsername();
        }

        usuarioService.crearUsuario(username, password, nombre, admin != null);

        return "redirect:/usuario/lista";
    }

    @GetMapping("/editar/{username}")
    public String editarUsuario(Model model, @PathVariable(name = "username") String username){

        Usuario usuario = securityService.getAuthorizedUser();
        Usuario usuarioListado = usuarioService.findByUsername(username);

        if (!(usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN") || usuario.getUsername().equalsIgnoreCase(username))){
            return "redirect:/usuario/lista/"+usuario.getUsername();
        }

        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("visualizar", "false");
        model.addAttribute("user", usuarioListado);
        model.addAttribute("accion","/usuario/editarPost");
        model.addAttribute("tipo","Guardar");

        return "thymeleaf/crudUsuario/editarUsuario";
    }

    @PostMapping("/editarPost")
    public String editarPost(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nombre") String nombre,
                             @RequestParam(name = "admin", required = false) String admin)
    {

        Usuario usuario = securityService.getAuthorizedUser();
        if (usuario == null){
            return "redirect:/";
        }

        if (!usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN")){
            return "redirect:/usuario/" + usuario.getUsername();
        }

        Rol rol;
        if (admin != null){
            rol = rolRepository.findByRole("ROLE_ADMIN");
        }else{
            rol = rolRepository.findByRole("ROLE_USER");
        }
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);

        Usuario editUsuario = new Usuario();
        editUsuario.setUsername(username);
        editUsuario.setPassword(password);
        editUsuario.setNombre(nombre);
        editUsuario.setRols(roles);

        usuarioService.editar(usuario);

        return "redirect:/usuario/lista";
    }

    @GetMapping("/eliminar/{username}")
    public String eliminar(@PathVariable(name = "username") String username){

        Usuario usuario = securityService.getAuthorizedUser();
        if(!usuario.getUsername().equals(username)){
            usuarioService.eliminar(username);
        }

        return "redirect:/usuario/lista";
    }


}

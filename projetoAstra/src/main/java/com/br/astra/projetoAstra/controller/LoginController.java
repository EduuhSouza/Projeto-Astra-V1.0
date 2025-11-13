package com.br.astra.projetoAstra.controller;

import com.br.astra.projetoAstra.model.User;
import com.br.astra.projetoAstra.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserRepository ur;

    // LOGIN
    @PostMapping("/logar")
    public String loginUser(User user, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        User userLogado = this.ur.login(user.getEmail(), user.getPassword());

        if (userLogado != null) {
            // Cria sessão e salva informações do usuário logado
            HttpSession session = request.getSession();
            session.setAttribute("userId", userLogado.getId());
            session.setAttribute("userName", userLogado.getUsername());

            return "redirect:/";
        }

        model.addAttribute("erro", "Usuário inválido");
        return "index";
    }

    @GetMapping("/")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/index";
        }

        // Adiciona o nome do usuário na tela
        model.addAttribute("nome", session.getAttribute("userName"));
        return "dashboard";
    }

    // LOGIN PAGE
    @GetMapping("/index")
    public String login() {
        return "index";
    }

    // CADASTRO
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String cadastroUsuario(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/index";
        }
        ur.save(user);
        return "redirect:/index";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index?logout";
    }
}
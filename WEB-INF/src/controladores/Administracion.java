package controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.manager.UsuariosManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import utils.Utilitarios;

	@Controller
	public class Administracion {
		
		@Autowired
		UsuariosManager usuariosManager;
		Utilitarios util;
		
		@RequestMapping({"index.html"})
		public String vista1(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			sesion.removeAttribute("log");
			sesion.invalidate();
			model.addAttribute("mensajeR", "");
			return "index";
		}
		
		@RequestMapping({"validar.html"})
		public String acceso(Model model,HttpServletRequest request,HttpServletResponse res,@RequestParam String login,String passwd)  throws IOException  {
		
		HttpSession sesion=request.getSession();
		
		
		if(usuariosManager.acceso_datos(login,passwd)==1 ){
			String id_usuario = String.valueOf(usuariosManager.obtenerId(login,passwd));
			int estado = usuariosManager.obtenerEstado(id_usuario);
			if(estado==1){
				sesion.setAttribute("id_usuario", id_usuario);
				sesion.setAttribute("log", login);
				sesion.setAttribute("pass", passwd);
			
				if(id_usuario.equals("1")){
					System.out.println(id_usuario);
					res.sendRedirect("inicio.html");
					return "inicio";
				}else{
					List<?> nombre = this.usuariosManager.obtenerNombre(id_usuario);
					model.addAttribute("nombre_usuario",nombre);
					
					return "inicio2";
				}
			}else{
				model.addAttribute("mensajeR", "bloqueado");
				return "index";
			}
		}
		else{
			model.addAttribute("mensajeR", "error");
			return "index";
		}
			
		}
		@RequestMapping({"inicio.html"})
		public String inicio(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			String pass=(String) sesion.getAttribute("pass");
			
			String nombre = usuariosManager.obtenerLogin();
			model.addAttribute("loginUsuario",nombre);
			return "inicio";


		}
		@RequestMapping({"inicio2.html"})
		public String inicio2(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			System.out.print(login);
			String pass=(String) sesion.getAttribute("pass");
			
			String nombre = usuariosManager.obtenerLogin();
			model.addAttribute("loginUsuario",nombre);
			return "inicio2";


		}
		
		@RequestMapping({"error.html"})
		public String error(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			sesion.removeAttribute("log");
			sesion.invalidate();
			return "error";
		}
		
		@RequestMapping({"bienvenida.html"})
		public String bienvenida(Model model,HttpServletRequest request)  throws IOException  {
			return "bienvenida";
		}
		
	}
		
   
		
	
